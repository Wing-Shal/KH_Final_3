package com.kh.Final3.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PaymentDto {
	private int paymentNo;
	private String paymentTime;
	private String paymentName;
	private int paymentTotal;
	private int companyNo;
	private String paymentTid;
	private String paymentSid;
	private String paymentStatus;
}
