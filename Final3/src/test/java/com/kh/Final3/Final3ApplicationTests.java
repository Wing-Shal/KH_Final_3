package com.kh.Final3;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kh.Final3.dao.PaymentDao;
import com.kh.Final3.dto.PaymentDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class Final3ApplicationTests {
	
	@Autowired
	private PaymentDao paymentDao;
	
	@Test
	public void kakoPaySubscription() {
		List<PaymentDto> list = paymentDao.selectActiveSubscription();
		for(PaymentDto paymentDto : list) {
			System.out.println(paymentDto);
		}
		
	}

}
