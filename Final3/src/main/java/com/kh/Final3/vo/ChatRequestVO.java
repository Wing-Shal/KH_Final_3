package com.kh.Final3.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ChatRequestVO {
	private String token; //토큰
	private String messageContent; //메세지 내용
	private int chatroomNo; //채팅방 번호
	
	
//	private Integer messageReply; // 답글번호 null또는 메세지번호
//	private Integer attachNo; //첨부파일 번호 - 이것도 인티저인가 ..?
//	
//	//추가사항
//    private String messageType;    // 메시지 타입 (텍스트인지 이미지인지 파일인지)
//    private boolean isRead;        // 메시지 읽음 여부

}
