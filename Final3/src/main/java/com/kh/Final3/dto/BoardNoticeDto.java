package com.kh.Final3.dto;

import java.sql.Date;
import java.text.SimpleDateFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class BoardNoticeDto {
	private int noticeNo;
	private int companyNo;
	private String noticeTitle;
	private String noticeContent;
	private Date noticeWtime;
	private Date noticeEtime;
	
    public String getNoticeWtimeWithMinute() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(this.noticeWtime);
    }
    
    public String getNoticeEtimeWithMinute() {
        if (this.noticeEtime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.format(this.noticeEtime);
        }
        else {
            return null;
        }
    }
}
