package com.kh.Final3.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.kh.Final3.websocket.EmpWebsocketServer;

@CrossOrigin
@EnableWebSocket 
@Configuration 
public class WebSocketServerConfiguration implements WebSocketConfigurer{
	
	@Autowired
	private EmpWebsocketServer empWebsocketServer;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(empWebsocketServer, "/ws/emp")
					.addInterceptors(new HttpSessionHandshakeInterceptor())
					.setAllowedOriginPatterns("*")
					.withSockJS();
	}
	



}
