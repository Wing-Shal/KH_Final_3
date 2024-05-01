package com.kh.Final3.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.Final3.service.JwtService;
import com.kh.Final3.vo.LoginVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Adminintercepter implements HandlerInterceptor{

	@Autowired
	private JwtService jwtService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String method = request.getMethod();
		if(method.toLowerCase().equals("options")) return true;
		
		String token = request.getHeader("Authorization");
		//비로그인 사용자
		if(token == null) {
			response.sendError(401);
			return false;
		}
		try {
			LoginVO loginVO = jwtService.parse(token);
			int loginId = loginVO.getLoginId();
			String loginLevel = loginVO.getLoginLevel();
			if(loginLevel.equals("운영자")) {
				log.debug("운영자가 로그인했습니다./n아이디 = {}\n등급 = {}", loginId, loginLevel);
				return true;
			}
		} catch (Exception e) {
			response.sendError(403);
			return false;
		}
		return false;
	}
	
}
