package com.kh.Final3.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class BoardNoticeDto {
	private int noticeNo;
	private int companyNo;
	private String noticeTitle;
	private String noticeContent;
	private String noticeWriter;
	private Date noticeWtime;
}
