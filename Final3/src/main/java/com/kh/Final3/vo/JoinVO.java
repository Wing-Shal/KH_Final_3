package com.kh.Final3.vo;

import java.util.List;

import com.kh.Final3.dto.DeptDto;
import com.kh.Final3.dto.GradeDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class JoinVO {
	private String companyName;
	private String companyBn;
	private String companyPw;
	private String companyContact;
	private String companyEmail;
	private String companyZipcode;
	private String companyAddress1;
	private String companyAddress2;
	private List<GradeDto> grades;
	private List<DeptDto> depts;
}
