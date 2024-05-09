package com.kh.Final3.dto;

import java.sql.Date;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor 
public class MessageDto {
	private int messageNo;
	private int messageSender;
	private String messageContent;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date messageTime;
	private int chatroomNo;
//	private Integer messageReply;
	private int readCount;
	
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
