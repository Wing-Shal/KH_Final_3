package com.kh.Final3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AdminAttachDto {
	private int adminAttachNo;
	private int attachNo;
	private String attachName;
}
