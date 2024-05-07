package com.kh.Final3.kakaoPayVo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//정기결제(2회차) 요청을 위한 VO
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class KakaoPaySubscriptionRequestVO {
	private String cid;
	private String sid;
	private String partnerOrderId;
	private String partnerUserId;
	private String itemName;
	private int quantity;
	private int totalAmount;
	private int taxFreeAmount;
}
