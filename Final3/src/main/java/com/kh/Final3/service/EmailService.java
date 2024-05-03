package com.kh.Final3.service;

import java.text.DecimalFormat;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.kh.Final3.dao.CertDao;
import com.kh.Final3.dao.CompanyDao;
import com.kh.Final3.dto.CertDto;
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
				"[Planet] <<" + companyDto.getCompanyName() + 
				">>의 가입이 승인되었습니다.");
		message.setText("가입해주셔서 감사합니다 자세한 이용방법은 어쩌고저쩌고");
		
		sender.send(message);
	}
	
	public void sendVerifyMail(CompanyDto companyDto) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(companyDto.getCompanyEmail());
		message.setSubject("[Planet]가입을 위한 인증을 완료해 주세요");
		message.setText("[Planet] <<" + companyDto.getCompanyName() + 
				">>의 가입을 완료하시려면 사업자 등록증을 이 메일로 첨부하신 후 발송 해주세요"
				+ "해당 자료는 가입 인증 이후 폐기됩니다.");
		sender.send(message);
	}
	
	@Autowired
	private CertDao certDao;
	
	//인증번호 발송 - 주어진 이메일에 무작위 6자리 숫자를 전송
		public void sendCert(CompanyDto companyDto) {
			Random r = new Random();
			int number = r.nextInt(1000000);//000000 ~ 999999
			DecimalFormat fmt = new DecimalFormat("000000");
			
			//메일 발송
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(companyDto.getCompanyEmail());
			message.setSubject("[Planet] 인증번호 안내");
			message.setText("인증번호는 [" + fmt.format(number) + "] 입니다");
			
			sender.send(message);
			
			//인증번호 저장 - 기존 내역 삭제 후 저장
			certDao.delete(companyDto.getCompanyEmail());
			CertDto certDto = new CertDto();
			certDto.setCertEmail(companyDto.getCompanyEmail());
			certDto.setCertNumber(fmt.format(number));
			certDao.insert(certDto);
		}
	
}
