package com.kh.Final3.dto;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReplyBlindDto {
	 private int replyBlindNo; // 댓글 번호
	    private String replyBlindContent; // 댓글 내용
	    private String replyBlindNick; // 댓글 작성자
	    private String replyBlindCompany; // 댓글 작성자 회사
	    private String  replyBlindTime; // 댓글 작성 시각
	    private int replyEmpNo;
	    private int blindNo;

	 
	}