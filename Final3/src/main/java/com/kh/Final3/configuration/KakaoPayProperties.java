package com.kh.Final3.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import lombok.Data;

@Data
@Service
@ConfigurationProperties(prefix = "custom.pay")
public class KakaoPayProperties {
	private String key;
	private String cid;
	private String itemName;
	private int totalAmount;
}
