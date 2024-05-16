package com.kh.Final3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ProjectEmpListDto {

		private int projectNo;
		private int empNo;
		private String projectEmp;//프로젝트 초대자
		private int projectEmpListNo; //프로젝트에 참여한 사원 그룹 번호
	

}
