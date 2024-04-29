package com.kh.Final3.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class DocumentDto {
	private int documentNo; //문서 번호
	private int projectNo; //프로젝트 번호
	private String documentTitle; //문서 제목
	private String documentContent; //문서 내용
	private String documentStatus; //문서 상태
	private Date documentWriteTime; //문서 작성일
	private Date documentLimitTime; //문서결재 마감일
	private String documentWriter; //문서 작성자 
	private String documentApprover; //문서 결재자
	

}