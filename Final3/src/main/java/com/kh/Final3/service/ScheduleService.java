package com.kh.Final3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.kh.Final3.dao.PaymentDao;
import com.kh.Final3.kakaoPayVo.KakaoPaySubscriptionRequestVO;


@Service
public class ScheduleService {
	@Autowired
	private PaymentDao paymentDao;

	@Scheduled(cron = "0 0 * * * *")
	public void kakoPaySubscription() {
		
		
		
	}
}
