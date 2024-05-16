package com.kh.Final3.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.kh.Final3.configuration.KakaoPayProperties;
import com.kh.Final3.dao.CompanyDao;
import com.kh.Final3.dao.PaymentDao;
import com.kh.Final3.dto.CompanyDto;
import com.kh.Final3.dto.PaymentDto;
import com.kh.Final3.kakaoPayVo.KakaoPayApproveRequestVO;
import com.kh.Final3.kakaoPayVo.KakaoPayReadyRequestVO;
import com.kh.Final3.kakaoPayVo.KakaoPayReadyResponseVO;
import com.kh.Final3.kakaoPayVo.KakaoPaySubscriptionInactiveRequestVO;
import com.kh.Final3.kakaoPayVo.KakaoPaySubscriptionInactiveResponseVO;
import com.kh.Final3.kakaoPayVo.KakaoPaySubscriptionRequestVO;
import com.kh.Final3.kakaoPayVo.KakaoPaySubscriptionResponseVO;
import com.kh.Final3.kakaoPayVo.KakaoPaySubscriptionStatusRequestVO;
import com.kh.Final3.kakaoPayVo.KakaoPaySubscriptionStatusResponseVO;
import com.kh.Final3.kakaoPayVo.KakaoPayApproveResponseVO;

@Service
public class KakaoPayService {

	@Autowired
	private KakaoPayProperties kakaoPayProperties;

	@Autowired
	private RestTemplate template;

	@Autowired
	private HttpHeaders header;

	// 결제준비
	public KakaoPayReadyResponseVO ready(KakaoPayReadyRequestVO requestVO) throws URISyntaxException {
		// 주소 생성
		URI uri = new URI("https://open-api.kakaopay.com/online/v1/payment/ready");

		// 바디 생성
		Map<String, String> body = new HashMap<>();
		body.put("cid", kakaoPayProperties.getCid());
		body.put("partner_order_id", requestVO.getPartnerOrderId());
		body.put("partner_user_id", requestVO.getPartnerUserId());
		body.put("item_name", requestVO.getItemName());
		body.put("quantity", "1");
		body.put("total_amount", String.valueOf(requestVO.getTotalAmount()));
		body.put("tax_free_amount", "0");

		String page = "http://localhost:3000/#/kakaopay/purchase";
		body.put("approval_url", page + "Success");
		body.put("cancel_url", page + "Cancel");
		body.put("fail_url", page + "Fail");

		HttpEntity entity = new HttpEntity(body, header);

		return template.postForObject(uri, entity, KakaoPayReadyResponseVO.class);
	}

	// 승인요청
	public KakaoPayApproveResponseVO approve(KakaoPayApproveRequestVO requestVO) throws URISyntaxException {
		// 주소 생성
		URI uri = new URI("https://open-api.kakaopay.com/online/v1/payment/approve");

		// 바디 생성
		// MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		Map<String, String> body = new HashMap<>();
		body.put("cid", kakaoPayProperties.getCid());
		body.put("tid", requestVO.getTid());
		body.put("pg_token", requestVO.getPgToken());
		body.put("partner_order_id", requestVO.getPartnerOrderId());
		body.put("partner_user_id", requestVO.getPartnerUserId());

		// 통신 요청
		HttpEntity entity = new HttpEntity(body, header);// 헤더+바디

		return template.postForObject(uri, entity, KakaoPayApproveResponseVO.class);
	}

	// DB등록
	@Autowired
	private PaymentDao paymentDao;
	@Autowired
	private CompanyDao companyDao;

	@Transactional
	public void insertPayment(KakaoPayApproveResponseVO responseVO) {
		int paymentNo = paymentDao.paymentSequence();
		PaymentDto paymentDto = PaymentDto.builder()
				.paymentNo(paymentNo)
				.paymentTime(responseVO.getCreatedAt())
				.paymentName(responseVO.getItemName())
				.paymentTotal(responseVO.getAmount().getTotal())
				.companyNo(Integer.parseInt(responseVO.getPartnerUserId()))
				.paymentTid(responseVO.getTid())
				.paymentSid(responseVO.getSid())
				.build();
		paymentDao.insertPayment(paymentDto);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		CompanyDto companyDto = CompanyDto.builder()
				.companyNo(Integer.parseInt(responseVO.getPartnerUserId()))
				.companyChecked(timestamp)
				.build();
		companyDao.editUnit(companyDto);
	}

	// 정기 결제 준비(2회차)
	public KakaoPaySubscriptionResponseVO subscription(KakaoPaySubscriptionRequestVO requestVO)
			throws URISyntaxException {
		// 주소 생성
		URI uri = new URI("https://open-api.kakaopay.com/online/v1/payment/subscription");

		// 바디 생성
		Map<String, String> body = new HashMap<>();
		body.put("cid", kakaoPayProperties.getCid());
		body.put("sid", requestVO.getSid());
		body.put("partner_order_id", requestVO.getPartnerOrderId());
		body.put("partner_user_id", requestVO.getPartnerUserId());
		body.put("item_name", requestVO.getItemName());
		body.put("quantity", "1");
		body.put("total_amount", String.valueOf(requestVO.getTotalAmount()));
		body.put("tax_free_amount", "0");

		HttpEntity entity = new HttpEntity(body, header);

		return template.postForObject(uri, entity, KakaoPaySubscriptionResponseVO.class);
	}
	@Transactional
	public void insertPayment(KakaoPaySubscriptionResponseVO responseVO) {
		int paymentNo = paymentDao.paymentSequence();
		PaymentDto paymentDto = PaymentDto.builder()
				.paymentNo(paymentNo)
				.paymentTime(responseVO.getCreatedAt())
				.paymentName(responseVO.getItemName())
				.paymentTotal(responseVO.getAmount().getTotal())
				.companyNo(Integer.parseInt(responseVO.getPartnerUserId()))
				.paymentTid(responseVO.getTid())
				.paymentSid(responseVO.getSid())
				.build();
		paymentDao.insertPayment(paymentDto);
	}
	

	//정기 결제 상태 확인
	public KakaoPaySubscriptionStatusResponseVO subscriptionStatus(KakaoPaySubscriptionStatusRequestVO requestVO)
			throws URISyntaxException {
		URI uri = new URI("https://open-api.kakaopay.com/online/v1/payment/manage/subscription/status");

		Map<String, String> body = new HashMap<>();
		body.put("cid", kakaoPayProperties.getCid());
		body.put("sid", requestVO.getSid());

		HttpEntity entity = new HttpEntity(body, header);

		return template.postForObject(uri, entity, KakaoPaySubscriptionStatusResponseVO.class);
	}
	
	//정기 결제 취소(중지)
	public KakaoPaySubscriptionInactiveResponseVO subscriptionInactive(KakaoPaySubscriptionInactiveRequestVO requestVO)
			throws URISyntaxException {
		URI uri = new URI("https://open-api.kakaopay.com/online/v1/payment/manage/subscription/status");

		Map<String, String> body = new HashMap<>();
		body.put("cid", kakaoPayProperties.getCid());
		body.put("sid", requestVO.getSid());

		HttpEntity entity = new HttpEntity(body, header);

		return template.postForObject(uri, entity, KakaoPaySubscriptionInactiveResponseVO.class);
	}
	@Transactional
	public void insertInactivePayment(KakaoPaySubscriptionInactiveResponseVO responseVO, int companyNo) {
		int paymentNo = paymentDao.paymentSequence();
		PaymentDto paymentDto = PaymentDto.builder()
				.paymentNo(paymentNo)
				.paymentTime(responseVO.getCreatedAt())
				.companyNo(companyNo)
				.paymentSid(responseVO.getSid())
				.build();
		paymentDao.cancelSubscription(paymentDto);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		CompanyDto companyDto = CompanyDto.builder()
				.companyNo(companyNo)
				.companyChecked(timestamp)
				.build();
		companyDao.editUnit(companyDto);
	}
}
