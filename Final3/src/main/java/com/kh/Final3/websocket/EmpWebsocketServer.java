package com.kh.Final3.websocket;

import java.io.IOException;
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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.Final3.dao.ChatroomDao;
import com.kh.Final3.dao.MessageDao;
import com.kh.Final3.dto.ChatroomDto;
import com.kh.Final3.dto.MessageDto;
import com.kh.Final3.service.AttachService;
import com.kh.Final3.service.JwtService;
import com.kh.Final3.vo.ChatRequestVO;
import com.kh.Final3.vo.ReadMessageRequestVO;

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
	
	protected void handleMessageRead(WebSocketSession session, ReadMessageRequestVO readRequestVO) throws Exception {
//	    System.out.println("읽은 메시지: " + readRequestVO.getReadMessageNo());
	    
	    int empNo = jwtService.parse(readRequestVO.getToken()).getLoginId();

	    //메시지가 이미 읽힌 상태인지 확인
	    boolean isAlreadyRead = messageDao.isReadMessage(readRequestVO.getReadMessageNo(), empNo);
	    
//	    System.out.println(isAlreadyRead);
	    
	    //본인 메시지인지
	    boolean isMyMessage = empNo == readRequestVO.getMessageSender();
//	    System.out.println("보내는사람" + readRequestVO.getMessageSender());
	    
	    if (!isAlreadyRead && !isMyMessage) {
	        //메시지 읽음 카운트 업데이트
	        messageDao.updateReadCount(readRequestVO.getReadMessageNo());
	    } 
	    else if(!isAlreadyRead) {
	    	//읽음 상태 기록 (예: 읽은 시간 업데이트 등)
	    	messageDao.checkReadMessage(readRequestVO.getReadMessageNo(), empNo);
	    }
	    else {
//	        System.out.println("메시지 " + readRequestVO.getReadMessageNo() + "는(은) 이미 읽음 처리되었습니다.");
	    	return;
	    }
	}
	
	@Autowired
	private AttachService attachService;

//	@Override
//	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
//	    try {
//	        ObjectMapper mapper = new ObjectMapper();
//	        
//	        ByteBuffer byteBuffer = message.getPayload();
//	        byte[] payload;
//	        if (byteBuffer.hasArray()) {
//	            payload = byteBuffer.array();
//	        } 
//	        else {
//	            payload = new byte[byteBuffer.remaining()];
//	            byteBuffer.get(payload);
//	        }
//	        String payloadString = new String(payload, StandardCharsets.UTF_8);
//	        
//	        JsonNode rootNode = mapper.readTree(payloadString);
//	        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//	        
////	        if (rootNode.has("readMessageNo")) {
////	            ReadMessageRequestVO readRequestVO = mapper.treeToValue(rootNode, ReadMessageRequestVO.class);
////	            handleMessageRead(session, readRequestVO);
////	            return; // 읽음 처리 후 함수 종료
////	        }
//	        
//	        ChatRequestVO requestVO = mapper.treeToValue(rootNode, ChatRequestVO.class);
//	        processNewMessage(session, requestVO);
//	    } 
//	    catch (JsonProcessingException e) {
//	        System.err.println("JSON parsing error: " + e.getMessage());
//	    } 
////	    catch (IOException e) {
////	        System.err.println("Failed to process binary message: " + e.getMessage());
////	    } 
//	    catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		    //[1] 사용자가 보낸 메세지를 ChatRequestVO로 해석
		    ObjectMapper mapper = new ObjectMapper();
		    
		    JsonNode rootNode = mapper.readTree(message.getPayload());
		    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		    
		    if (rootNode.has("readMessageNo")) {
		        ReadMessageRequestVO readRequestVO = mapper.treeToValue(rootNode, ReadMessageRequestVO.class);
		        handleMessageRead(session, readRequestVO);
		        return;
		    }
		    
		    ChatRequestVO requestVO = mapper.treeToValue(rootNode, ChatRequestVO.class);
		    processNewMessage(session, requestVO);
	}
	
//	private void processNewMessage(WebSocketSession session, ChatRequestVO requestVO) throws Exception {
//	    String token = requestVO.getToken();
//	    Integer empNo = jwtService.parse(token).getLoginId();
//
//	    MessageDto messageDto = messageDao.insert(MessageDto.builder()
//						        .messageSender(empNo)
//						        .messageContent(requestVO.getMessageContent())
//						        .chatroomNo(requestVO.getChatroomNo())
//						        .readCount(0)
//						      .build());
//
//	    String senderName = sqlSession.selectOne("message.listSetName", messageDto.getMessageNo());
//	    String senderGrade = sqlSession.selectOne("message.listSetGrade", messageDto.getMessageNo());
//	    messageDto.setMessageSenderName(senderName);
//	    messageDto.setMessageSenderGrade(senderGrade);
//
//	    String json = new ObjectMapper().writeValueAsString(messageDto);
//	    TextMessage response = new TextMessage(json);
//	    
//
//	    for (WebSocketSession user : users) {
//	        user.sendMessage(response);
//	    }
//	}
	
	private void processNewMessage(WebSocketSession session, ChatRequestVO requestVO) {
	    try {
	        String token = requestVO.getToken();
	        Integer empNo = jwtService.parse(token).getLoginId();

	        MessageDto messageDto = messageDao.insert(MessageDto.builder()
	                                        .messageSender(empNo)
	                                        .messageContent(requestVO.getMessageContent())
	                                        .chatroomNo(requestVO.getChatroomNo())
	                                        .readCount(0)
	                                      .build());

	        String senderName = sqlSession.selectOne("message.listSetName", messageDto.getMessageNo());
	        String senderGrade = sqlSession.selectOne("message.listSetGrade", messageDto.getMessageNo());
	        
//	        System.out.println(requestVO.getChatroomNo());
	        
	        Integer readCountForChatroom = chatroomDao.numberOfParticipants(requestVO.getChatroomNo()) - 1;
	        
//	        System.out.println(readCountForChatroom);
//	        System.out.println(messageDto.getReadCount());
	        
	        messageDto.setMessageSenderName(senderName);
	        messageDto.setMessageSenderGrade(senderGrade);
	        messageDto.setReadCountForChatroom(readCountForChatroom - messageDto.getReadCount());
	        
//	        System.out.println(messageDto.getReadCountForChatroom());

	        String json = new ObjectMapper().writeValueAsString(messageDto);
	        TextMessage response = new TextMessage(json);

	        for (WebSocketSession user : users) {
	            if (user.isOpen()) {
	                user.sendMessage(response);
	            }
	        }
	    } 
	    catch (Exception e) {
	        System.err.println("Failed to process new message: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	



		
		


}