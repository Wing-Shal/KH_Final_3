package com.kh.Final3.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class MessageInfoVO {
    private int messageNo;
    private int chatroomNo;
    private String messageContent;
    private Date messageTime;
    private int unreadCount;
}
