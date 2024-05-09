package com.kh.Final3.vo;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class RecentMessageVO {
	private int chatroomNo;
	private String chatroomName;
	private String messageContent;
	private Date messageTime;
	
    public String getMessageTimeMinute() {
        Calendar now = Calendar.getInstance();
        Calendar messageCalendar = Calendar.getInstance();
        messageCalendar.setTime(messageTime);

        //현재 시간과 메시지 시간의 차이 계산
        long diffMillis = now.getTimeInMillis() - messageTime.getTime();
        long diffHours = diffMillis / (60 * 60 * 1000);
        long diffDays = diffMillis / (24 * 60 * 60 * 1000);

        //오늘 전송한 경우
        if (diffHours < 24) {
            SimpleDateFormat formatter = new SimpleDateFormat("a hh:mm");
            return formatter.format(messageTime);
        }
        //어제 전송한 경우
        else if (diffDays == 1) {
            return "어제";
        }
        //그 이상 전송한 경우
        else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(messageTime);
        }
    }
}
