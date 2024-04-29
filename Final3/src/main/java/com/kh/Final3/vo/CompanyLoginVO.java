package com.kh.Final3.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyLoginVO {
    private int companyNo;
	private String companyLevel;
    private String accessToken;
    private String refreshToken;
}
