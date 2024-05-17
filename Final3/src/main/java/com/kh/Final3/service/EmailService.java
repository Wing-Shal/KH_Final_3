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
        message.setSubject("[Planet] <<" + companyDto.getCompanyName() + ">>의 가입이 승인되었습니다.");
        message.setText(
                "안녕하세요, " + companyDto.getCompanyName() + ".\n\n" +
                "Planet 가입이 승인되었습니다. 가입해 주셔서 감사합니다.\n\n" +
                "실제 시스템을 이용하시려면 다음 단계를 따라주세요:\n" +
                "1. 플래닛 페이지로 이동합니다.\n" +
                "2. 승인된 계정으로 로그인합니다.\n" +
                "3. 정기결제를 진행합니다.\n\n" +
                "문의사항이 있으시면 언제든지 고객센터로 연락해 주세요.\n\n" +
                "감사합니다.\n" +
                "Planet 팀 드림"
        );
        sender.send(message);
    }

    public void sendVerifyMail(CompanyDto companyDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(companyDto.getCompanyEmail());
        message.setSubject("[Planet] 가입을 위한 인증을 완료해 주세요");
        message.setText(
                "안녕하세요, " + companyDto.getCompanyName() + "님.\n\n" +
                "Planet 가입을 완료하시려면 사업자 등록증을 이 메일로 첨부하신 후 발송해 주세요.\n\n" +
                "해당 자료는 가입 인증 이후 폐기됩니다.\n\n" +
                "감사합니다.\n" +
                "Planet 팀 드림"
        );
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
