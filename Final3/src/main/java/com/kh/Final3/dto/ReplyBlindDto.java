package com.kh.Final3.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReplyBlindDto {

	private int replyBlindNo; //댓글 조회수
	private String replyBlindContent; //댓글 내용
	private String replyBlindNick; //댓글 작성자
	private String replyBlindCompany; //댓글 작성자 회사
	private Date replyBlindTime; //댓글작성시각
	private int replyBlindTarget; //댓글 타겟(기존댓글)
	private int replyBlindGroup; //그룹
}