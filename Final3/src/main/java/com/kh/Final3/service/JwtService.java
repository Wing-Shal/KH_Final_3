package com.kh.Final3.service;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.Final3.configuration.JwtProperties;
import com.kh.Final3.dto.AdminDto;
import com.kh.Final3.vo.AdminLoginVO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Autowired
	private JwtProperties jwtProperties;
	
	public String createAccessToken(AdminDto adminDto) {
		String KeyStr = jwtProperties.getKeyStr();
		SecretKey key = Keys.hmacShaKeyFor(KeyStr.getBytes());
		
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		c.add(Calendar.HOUR, jwtProperties.getExpireHour());
		Date expire = c.getTime();
		
		String token = Jwts.builder()
						.issuer(jwtProperties.getIssuer())
						.issuedAt(now)
						.expiration(expire)
						.signWith(key)
						.claim("loginId", adminDto.getAdminId())
					.compact();
		return token;
	}
	
	public String createRefreshToken(AdminDto adminDto) {
		String KeyStr = jwtProperties.getKeyStr();
		SecretKey key = Keys.hmacShaKeyFor(KeyStr.getBytes());
		
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		c.add(Calendar.HOUR, jwtProperties.getExpireHourRefresh());
		Date expire = c.getTime();
		
		String token = Jwts.builder()
						.issuer(jwtProperties.getIssuer())
						.issuedAt(now)
						.expiration(expire)
						.signWith(key)
						.claim("loginId", adminDto.getAdminId())
					.compact();
		return token;
	}
	
	public AdminLoginVO parse(String token) {
		String keyStr = jwtProperties.getKeyStr();
		SecretKey key = Keys.hmacShaKeyFor(keyStr.getBytes(StandardCharsets.UTF_8));
		
		Claims claims = (Claims) Jwts.parser()
										.verifyWith(key)
										.requireIssuer(jwtProperties.getIssuer())
										.build()
										.parse(token)
										.getPayload();
		return AdminLoginVO.builder()
						.adminId((String) claims.get("loginId"))
					.build();
	}
}
