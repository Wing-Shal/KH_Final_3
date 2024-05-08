package com.kh.Final3.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@Builder@NoArgsConstructor@AllArgsConstructor
public class LoginVO {
	private Integer loginId;
	private String loginLevel;
	private String isPaid;
	private String accessToken;
	private String refreshToken;
}
