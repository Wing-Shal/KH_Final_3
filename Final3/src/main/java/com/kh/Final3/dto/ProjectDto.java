
package com.kh.Final3.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ProjectDto {
	private int projectNo; //프로젝트번호
	private String projectName; //프로젝트 이름
	private Date projectStartTime; //프로젝트 시작일
	private Date projectLimitTime ; //프로젝트 종료일
	private int companyNo; //회사번호
	private String projectWriter; //프로젝트 작성자
	private int empNo;
	private String token;
	private String referencePerson; //참조자
//	private String approver; //결재자
	//private String empName;

}
