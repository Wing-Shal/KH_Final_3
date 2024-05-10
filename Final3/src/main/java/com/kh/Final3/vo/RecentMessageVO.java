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
	    if (messageTime == null) {
	        return ""; // 또는 다른 적절한 기본값을 반환
	    }
	    Calendar now = Calendar.getInstance();
	    Calendar messageCalendar = Calendar.getInstance();
	    messageCalendar.setTime(messageTime);

	    // 메시지 시간의 날짜를 기준으로 오늘/어제 여부 판단
	    boolean isToday = now.get(Calendar.YEAR) == messageCalendar.get(Calendar.YEAR) &&
	                      now.get(Calendar.DAY_OF_YEAR) == messageCalendar.get(Calendar.DAY_OF_YEAR);

	    // 메시지 시간의 시간을 기준으로 오늘/어제 여부 판단
	    boolean isYesterday = now.get(Calendar.YEAR) == messageCalendar.get(Calendar.YEAR) &&
	                          now.get(Calendar.DAY_OF_YEAR) - messageCalendar.get(Calendar.DAY_OF_YEAR) == 1;

	    // 오늘 전송한 경우
	    if (isToday) {
	        SimpleDateFormat formatter = new SimpleDateFormat("a hh:mm");
	        return formatter.format(messageTime);
	    }
	    // 어제 전송한 경우
	    else if (isYesterday) {
	        return "어제";
	    }
	    // 그 이상 전송한 경우
	    else {
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        return formatter.format(messageTime);
	    }
	}
}
