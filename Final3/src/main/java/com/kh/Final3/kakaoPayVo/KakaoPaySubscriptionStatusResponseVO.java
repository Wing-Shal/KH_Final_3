package com.kh.Final3.kakaoPayVo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//정기결제 응답VO
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class KakaoPaySubscriptionStatusResponseVO {
	private boolean available;
	private String cid;
	private String sid;
	private String status;
	private String paymentMethodType;
	private String itemName;
	private String createdAt;
	private String lastApprovedAt;
	private String inactivedAt;
	private String usePointStatus;
}
