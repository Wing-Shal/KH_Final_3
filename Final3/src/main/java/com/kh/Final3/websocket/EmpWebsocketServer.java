package com.kh.Final3.websocket;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.Final3.dao.ChatroomDao;
import com.kh.Final3.dao.MessageDao;
import com.kh.Final3.dto.ChatroomDto;
import com.kh.Final3.dto.MessageDto;
import com.kh.Final3.service.JwtService;
import com.kh.Final3.vo.ChatRequestVO;

@Service
public class EmpWebsocketServer extends TextWebSocketHandler {
	
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private ChatroomDao chatroomDao;
	
	@Autowired
	private JwtService jwtService;
	
	
	@Autowired
	private SqlSession sqlSession;
	
	//사용자의 정보를 저장할 저장소 생성
	private Map<String, ChatroomDto> chatrooms = Collections.synchronizedMap(new HashMap<>());
	private Set<WebSocketSession> users = new CopyOnWriteArraySet<>();//동기화 됨(자물쇠 있음)
	

	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		users.add(session);
        // WebSocket 세션에서 채팅방 번호를 추출
        String chatroomNo = (String) session.getAttributes().get("chatroomNo");
        
        
        
        
        if (chatroomNo != null) {
            ChatroomDto chatroomDto = chatrooms.get(chatroomNo);
            
            
            if (chatroomDto != null) {
                List<MessageDto> list = messageDao.selectList(chatroomDto.getChatroomNo());
                ObjectMapper mapper = new ObjectMapper();
                
                for (MessageDto messageDto : list) {

                    String json = mapper.writeValueAsString(messageDto);
                    byte[] jsonData = json.getBytes(StandardCharsets.UTF_8);
                    ByteBuffer byteBuffer = ByteBuffer.wrap(jsonData);
                    BinaryMessage message = new BinaryMessage(byteBuffer.array());
                    session.sendMessage(message);
                }
            }
        }
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		users.remove(session);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		    //[1] 사용자가 보낸 메세지를 ChatRequestVO로 해석
		    ObjectMapper mapper = new ObjectMapper();
		    ChatRequestVO requestVO = mapper.readValue(message.getPayload(), ChatRequestVO.class);
		    String token = requestVO.getToken();
		    Integer empNo = jwtService.parse(token).getLoginId();
		    

		    
		    //(+추가) 이 시점에 DB에 메세지를 등록하도록 코드 추가
		    MessageDto messageDto = messageDao.insert(MessageDto.builder()
																	    		.messageSender(empNo)
																	    		.messageContent(requestVO.getMessageContent())
																	    		.chatroomNo(requestVO.getChatroomNo())
																    		.build());
		    String senderName = sqlSession.selectOne("message.listSetName", messageDto.getMessageNo());
		    String senderGrade = sqlSession.selectOne("message.listSetGrade", messageDto.getMessageNo());
		    messageDto.setMessageSenderName(senderName);
		    messageDto.setMessageSenderGrade(senderGrade);
		    
		  //[3] 메세지 객체 생성
		    String json = mapper.writeValueAsString(messageDto);
		    TextMessage response = new TextMessage(json);
		    
//	    System.out.println("보내는사람 = " + empNo);
//		users.forEach(System.out::println);
		    
		for(WebSocketSession user : users) {
			user.sendMessage(response);
		}
	}


		
		


}
