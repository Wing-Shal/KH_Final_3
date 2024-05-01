package com.kh.Final3.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class EmpChatroomDto {
	private int empNo;
	private int chatroomNo;
}
