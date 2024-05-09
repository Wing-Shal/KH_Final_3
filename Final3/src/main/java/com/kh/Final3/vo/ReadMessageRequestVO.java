package com.kh.Final3.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReadMessageRequestVO {
    private int messageNo;  // 읽은 메시지 번호
    private int chatroomNo; // 채팅방 번호
    
    private String token;

}
