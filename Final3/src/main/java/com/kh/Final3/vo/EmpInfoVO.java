package com.kh.Final3.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class EmpInfoVO {
	private int empNo;
	private String empName;
	private String empContact;
	private String empEmail;
	private String gradeName;
	private String deptName;
	private String empStatus;
	private Date empJoin;
	private Date empExit;
}
