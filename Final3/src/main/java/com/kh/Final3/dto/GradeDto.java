package com.kh.Final3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class GradeDto {
	private int gradeNo;
	private int companyNo;
	private String gradeName;
}
