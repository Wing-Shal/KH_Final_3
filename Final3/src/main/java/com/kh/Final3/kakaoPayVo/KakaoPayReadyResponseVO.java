package com.kh.Final3.kakaoPayVo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//카카오페이 준비요청이 완료되었을 때 카카오페이에서 보내주는 정보를 담을 VO
//(카카오페이가 보내주면 자동으로 생성되어야 함 - JSON 변환 설정을 추가)
@JsonIgnoreProperties(ignoreUnknown = true)//혹시 없는항목은 넘어가줘...
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)//카멜케이스로 좀 바꿔줘
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class KakaoPayReadyResponseVO {
	private String tid;//결제 고유번호(20자)
	private String nextRedirectMobileUrl;//모바일 웹용 결제페이지 주소
	private String nextRedirectPcUrl;//PC용 결제페이지 주소
	private String createdAt;//결제 준비 요청 시각
}