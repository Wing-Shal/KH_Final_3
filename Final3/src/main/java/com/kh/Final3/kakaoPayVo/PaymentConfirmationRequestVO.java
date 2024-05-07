package com.kh.Final3.kakaoPayVo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class PaymentConfirmationRequestVO {
	private String partnerOrderId;
	private String partnerUserId;
	private String tid;
	private String pgToken;
}
