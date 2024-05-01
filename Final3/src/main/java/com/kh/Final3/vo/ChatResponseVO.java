package com.kh.Final3.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ChatResponseVO {
	private String messageContent; //메세지 내용
	private String messageTime; //전송 시각
	
	private String messageSender; //전송한 사람
	private int chatroomNo; //채팅방 번호
	
//	private Integer messageReply; //답글번호 // 생각해보니 null일수도 있어서 인티저로 했음
	
	
}
