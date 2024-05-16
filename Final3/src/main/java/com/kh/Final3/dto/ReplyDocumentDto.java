package com.kh.Final3.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReplyDocumentDto {
	private int replyDocumentNo; //문서 댓글 번호
	private String replyDocumentContent; //댓글 내용
	private String replyDocumentNick; //댓글 작성자
	private String replyDocumentCompany; //댓글 작성자 회사
	private Date replyDocumentTime; //댓글작성시각
	private int replyDocumentTarget; //댓글 타겟(기존댓글)
	private int replyDocumentGroup; //그룹
	private int documnetNo; //문서번호
}
