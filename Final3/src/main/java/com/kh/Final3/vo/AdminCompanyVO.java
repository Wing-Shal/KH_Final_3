package com.kh.Final3.vo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AdminCompanyVO {
	private int companyNo;//회사 코드
	private String companyName;//회사명
	private String companyBn;//사업자번호
	private String companyContact;//연락처
	private String companyEmail;//이메일
	private String companyZipcode;//우편번호
	private String companyAddress1;//주소1
	private String companyAddress2;//주소2
	private Timestamp companyChecked;//인증 시각
	private String paymentStatus;
}
