package com.kh.Final3.dto;

import java.sql.Date;
import java.text.SimpleDateFormat;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor 
public class MessageDto {
	private int messageNo;
	private int messageSender;
	private String messageContent;
	private Date messageTime;
	private int chatroomNo;
//	private Integer messageReply;
	private int readCount;
	
	private int readCountForChatroom;
	
	private String messageSenderName;
	private String messageSenderGrade;
	
    public String getMessageTimeMinute() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (messageTime != null) {
            return formatter.format(messageTime);
        }
        return ""; 
    }

}
