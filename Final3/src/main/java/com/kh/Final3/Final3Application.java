package com.kh.Final3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//Spring Security 기본 시스템 설정 해제
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Final3Application {

	public static void main(String[] args) {
		SpringApplication.run(Final3Application.class, args);
	}

}
