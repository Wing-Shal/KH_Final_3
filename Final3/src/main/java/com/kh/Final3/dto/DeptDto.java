package com.kh.Final3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class DeptDto {
	private int deptNo;//부서 번호
	private int companyNo;//회사 코드
	private String deptName;//부서명
}