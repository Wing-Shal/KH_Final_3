package com.kh.Final3.service;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.kh.Final3.configuration.KakaoPayProperties;
import com.kh.Final3.dao.PaymentDao;
import com.kh.Final3.dto.PaymentDto;
import com.kh.Final3.kakaoPayVo.KakaoPaySubscriptionRequestVO;
import com.kh.Final3.kakaoPayVo.KakaoPaySubscriptionResponseVO;


@Service
public class ScheduleService {
	@Autowired
	private PaymentDao paymentDao;
	@Autowired
	private KakaoPayService kakaoPayService;
	@Autowired
	private KakaoPayProperties kakaoPayProperties;

	@Scheduled(cron = "0 0 */4 * * *")
	public void kakoPaySubscription() throws URISyntaxException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		LocalDate today = LocalDate.now();
		YearMonth currentMonth = YearMonth.of(today.getYear(), today.getMonth());
		LocalDate lastDayOfMonth = currentMonth.atEndOfMonth();
		
		List<PaymentDto> list = paymentDao.selectActiveSubscription();
		for(PaymentDto paymentDto : list) {
			LocalDate date = LocalDate.parse(paymentDto.getPaymentTime(), formatter);
			int dayOfMonth = date.getDayOfMonth();
			
			LocalDate paymentDay;
			
			//결제일이 이번달의 말일보다 뒤면 말일로 설정
			if(dayOfMonth > lastDayOfMonth.getDayOfMonth()) {
				paymentDay = lastDayOfMonth;
			} else {
				paymentDay = LocalDate.of(today.getYear(), today.getMonth(), dayOfMonth);
			}
			//결제일이 오늘과 같다면(오늘 결제했다면) 제외
			if((paymentDay.getDayOfMonth() == today.getDayOfMonth()) && 
					!paymentDay.equals(today)) {
				KakaoPaySubscriptionRequestVO requestVO =new KakaoPaySubscriptionRequestVO();
				requestVO.setCid(kakaoPayProperties.getCid());
				requestVO.setSid(paymentDto.getPaymentSid());
				
				KakaoPaySubscriptionResponseVO responseVO = kakaoPayService.subscription(requestVO);
				
				kakaoPayService.insertPayment(responseVO);
			}
		}
		
	}
}
