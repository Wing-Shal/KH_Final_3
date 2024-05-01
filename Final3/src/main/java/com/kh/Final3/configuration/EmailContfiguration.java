package com.kh.Final3.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration //설정 파일 등록
public class EmailContfiguration {

	@Autowired
	private EmailProperties emailProperties;
	
	@Bean
	public JavaMailSenderImpl sender() {
		//이메일 전송 도구
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost("smtp.gmail.com");//업체의 호스트정보
		sender.setPort(587);//업체의 포트 번호(구글)
		sender.setUsername(emailProperties.getId());//계정
		sender.setPassword(emailProperties.getPw());//비밀번호
		
		//통신과 관련된 추가 설정
		Properties props =  new Properties();//자바에서 제공하는 문자열 Map
		props.setProperty("mail.smtp.auth", "true");//인증 후 이용설정(필수)
		props.setProperty("mail.smtp.debug", "true");//디버깅 이용 설정(선택)
		props.setProperty("mail.smtp.starttls.enable", "true");//TLS 사용 설정(필수)
		props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");//TLS 버전 설정(필수)
		props.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");//신뢰할 수 있는 대상으로 설정(필수)
		sender.setJavaMailProperties(props);
		
		return sender;
	}
}
