package com.kh.Final3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@ToString(exclude = {"companyPw"})
public class CompanyDto {
	private int companyNo;//회사 코드
	private String companyPw;//회사 비밀번호
	private String companyName;//회사명
	private String companyBn;//사업자번호
	private String companyContact;//연락처
	private String companyEmail;//이메일
	private String companyZipcode;//우편번호
	private String companyAddress1;//주소1
	private String companyAddress2;//주소2
}