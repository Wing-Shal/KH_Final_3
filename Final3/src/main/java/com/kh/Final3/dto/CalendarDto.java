package com.kh.Final3.dto;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Tag(name = "캘린더")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CalendarDto {
	private int calendarNo; //캘린더번호
	private int calendarWriter; //작성자
	private String calendarTitle; //제목
	private String calendarContent; //상세내용(클릭하면 모달로)
	private String calendarStart; //시작일자
	private String calendarEnd; //종료일자
	
	private String empName; //이름
	private String empGrade; //직급
}

