package com.kh.Final3.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//블라인드 게시판 게시글 삭제하려면 비밀번호가 필요 (추가?)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardBlindDto {
	private int blindNo;//게시판 번호
	private String blindTitle; // 게시판 제목
	private String blindContent; // 게시판 내용
	private String blindWriterNick; // 작성자
	private String blindWriterCompany; // 회사이름
	private Date blindWtime; //게시글 작성시각
	private Date blindEtime; //게시글 수정시각
	private int blindView; //게시글 조회수
	private String blindPassword; //게시글비밀번호
	private int blindEmpNo;//테이블에도 참조하는 사원번호를 추가해야할 것 같은데?
	private int companyNo; 
}

