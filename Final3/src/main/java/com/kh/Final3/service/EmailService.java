package com.kh.Final3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.kh.Final3.dao.CompanyDao;
import com.kh.Final3.dto.CompanyDto;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender sender;
	@Autowired
	private CompanyDao companyDao;
	
	public void sendApproveMail(CompanyDto companyDto) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(companyDto.getCompanyEmail());
		message.setSubject(
				"[임시회사이름] <<" + companyDto.getCompanyName() + 
				">>의 가입이 승인되었습니다.");
		message.setText("가입해주셔서 감사합니다 자세한 이용방법은 어쩌고저쩌고");
		
		sender.send(message);
	}
}
