package com.kh.Final3.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class EmpInputVO {
	private String empName;
	private String deptName;
	private String gradeName;
	private String empContact;
	private String empEmail;
	private String empType;
}
