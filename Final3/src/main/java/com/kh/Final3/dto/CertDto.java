package com.kh.Final3.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CertDto {
	private String certEmail;
	private String certNumber;
	private Date certTime;
}
