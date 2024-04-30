package com.kh.Final3.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ChatRequestVO {
	private String messageContent; //메세지 내용
	private String messageTime; //전송 시각
	
	private String messageSender; //전송한 사람
	private int chatroomNo; //채팅방 번호
	
	private Integer messageReply; // 답글번호 null또는 메세지번호 - 자기참조
	private Integer attachNo; //첨부파일 번호 - 이것도 인티저인가 ..?
	
	//추가사항
    private String messageType;    // 메시지 타입 (텍스트인지 이미지인지 파일인지)
    private boolean isRead;        // 메시지 읽음 여부

}
