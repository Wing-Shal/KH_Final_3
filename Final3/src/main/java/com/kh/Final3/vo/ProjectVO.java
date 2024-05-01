package com.kh.Final3.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ProjectVO {
		private String token;
		private String projectWriter; //프로젝트 작성자
		private int empNo; //사원번호
		private String empName; //사원이름
		private int projectNo; //프로젝트 번호
		

	
}
