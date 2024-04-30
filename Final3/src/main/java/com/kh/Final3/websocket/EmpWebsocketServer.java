package com.kh.Final3.websocket;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import com.kh.Final3.dao.MessageDao;
import com.kh.Final3.dto.ChatroomDto;

@Service
public class EmpWebsocketServer extends BinaryWebSocketHandler{
	
	//사용자의 정보를 저장할 저장소 생성
	private Map<String, ChatroomDto> chatroom = Collections.synchronizedMap(new HashMap<>());
	
	@Autowired
	private MessageDao messageDao;
	
	
	
	

}
