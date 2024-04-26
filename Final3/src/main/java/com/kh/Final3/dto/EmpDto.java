package com.kh.Final3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@ToString(exclude = {"empPw"})
public class EmpDto {
	private int empNo;//사원 번호
	private String empPw;//사원 비밀번호
	private String empName;//사원명
	private String empContact;//연락처
	private String empEmail;//이메일
	private String empPr;//자기소개
	private String empType;//사원 or 임원
	private int gradeNo;//직급번호
	private int companyNo;//직급번호
	private int deptNo;//부서번호
	private String empStatus;//사원 상태
	private Date empJoin;//입사일
	private Date empExit;//퇴사일
	
}