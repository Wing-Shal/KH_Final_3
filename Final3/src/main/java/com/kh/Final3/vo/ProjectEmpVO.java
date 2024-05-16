package com.kh.Final3.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ProjectEmpVO {
	private int companyNo;
	private int empNo;
	private String empName;
	private String gradeName;
	private String deptName;

}
