package com.kh.Final3.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CountUnreadMessageVO {
	private int empNo;
	private int chatroomNo;

}
