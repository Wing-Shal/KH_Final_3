package com.kh.Final3.restcontroller;

import java.net.URISyntaxException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.Final3.configuration.KakaoPayProperties;
import com.kh.Final3.dao.PaymentDao;
import com.kh.Final3.dto.PaymentDto;
import com.kh.Final3.kakaoPayVo.KakaoPayApproveRequestVO;
import com.kh.Final3.kakaoPayVo.KakaoPayApproveResponseVO;
import com.kh.Final3.kakaoPayVo.KakaoPayReadyRequestVO;
import com.kh.Final3.kakaoPayVo.KakaoPayReadyResponseVO;
import com.kh.Final3.kakaoPayVo.KakaoPaySubscriptionInactiveRequestVO;
import com.kh.Final3.kakaoPayVo.KakaoPaySubscriptionInactiveResponseVO;
import com.kh.Final3.kakaoPayVo.PaymentConfirmationRequestVO;
import com.kh.Final3.kakaoPayVo.PaymentInitiationInfoVO;
import com.kh.Final3.service.JwtService;
import com.kh.Final3.service.KakaoPayService;
import com.kh.Final3.vo.LoginVO;

@CrossOrigin
@RestController
@RequestMapping("/kakaopay")
public class KakaoPayRestctroller {
	
	@Autowired
	private KakaoPayService kakaoPayService;
	
	@Autowired
	private JwtService jwtService;
	
	@GetMapping("/purchase")
	public PaymentInitiationInfoVO purchase(@RequestHeader("Authorization") String token) throws URISyntaxException {
		LoginVO loginVO = jwtService.parse(token);
		
		//주문번호를 생성
		KakaoPayReadyRequestVO requestVO = new KakaoPayReadyRequestVO();
		requestVO.setPartnerOrderId(UUID.randomUUID().toString());
		requestVO.setPartnerUserId(Integer.toString(loginVO.getLoginId()));
		requestVO.setItemName("Planet 정기 이용권");
		requestVO.setTotalAmount(990000);
		
		KakaoPayReadyResponseVO responseVO = kakaoPayService.ready(requestVO);
//		System.out.println(responseVO);
		PaymentInitiationInfoVO paymentInitiationInfoVO = new PaymentInitiationInfoVO();
		paymentInitiationInfoVO.setPartnerOrderId(requestVO.getPartnerOrderId());
		paymentInitiationInfoVO.setPartnerUserId(requestVO.getPartnerUserId());
		paymentInitiationInfoVO.setTid(responseVO.getTid());
		paymentInitiationInfoVO.setNextRedirectPcUrl(responseVO.getNextRedirectPcUrl());
		
		return paymentInitiationInfoVO;
	}
	@PostMapping("/purchase/success")
	public void success(@RequestBody PaymentConfirmationRequestVO paymentConfirmationRequestVO) throws URISyntaxException {
		KakaoPayApproveRequestVO requestVO = 
				KakaoPayApproveRequestVO.builder()
					.partnerOrderId(paymentConfirmationRequestVO.getPartnerOrderId())
					.partnerUserId(paymentConfirmationRequestVO.getPartnerUserId())
					.tid(paymentConfirmationRequestVO.getTid())
					.pgToken(paymentConfirmationRequestVO.getPgToken())
				.build();
		
		KakaoPayApproveResponseVO responseVO = kakaoPayService.approve(requestVO);
		
		//DB등록
		kakaoPayService.insertPayment(responseVO);
	}
	
	@Autowired
	private PaymentDao paymentDao;
	@Autowired
	private KakaoPayProperties kakaoPayProperties;
	
	@GetMapping("/purchase/cancel")
	public void cancel(@RequestHeader("Authorization") String token) throws URISyntaxException {
		LoginVO loginVO = jwtService.parse(token);
		int companyNo = loginVO.getLoginId();
		//사번으로 가장 최근 결제정보 가져오기
		PaymentDto paymentDto = paymentDao.selectListByCompanyNo(companyNo).get(0);
		
		//정기결제 비활성화 요청
		KakaoPaySubscriptionInactiveRequestVO requestVO = 
				KakaoPaySubscriptionInactiveRequestVO.builder()
					.cid(kakaoPayProperties.getCid())
					.sid(paymentDto.getPaymentSid())
				.build();
		
		KakaoPaySubscriptionInactiveResponseVO responseVO = kakaoPayService.subscriptionInactive(requestVO);
		
		//DB에 추가		
		kakaoPayService.insertInactivePayment(responseVO, companyNo);
		
	}
	
}
