
package com.kh.Final3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ProjectDto {
	private int projectNo; //프로젝트번호
	private String projectName; //프로젝트 이름
	private Data projectStartTime; //프로젝트 시작일
	private Data projectLimitTime ; //프로젝트 시작일
	private int companyNo; //회사번호
	private String projectWriter; //프로젝트 작성자

}
