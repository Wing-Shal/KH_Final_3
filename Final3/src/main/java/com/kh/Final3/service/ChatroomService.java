package com.kh.Final3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.Final3.dao.ChatroomDao;
import com.kh.Final3.dto.ChatroomDto;

@Service
public class ChatroomService {
    
    @Autowired
    private ChatroomDao chatroomDao;

    // 채팅방 찾기 또는 생성
    public ChatroomDto findOrCreateChatroom(int empNo1, int empNo2) {
        Integer chatroomNo = chatroomDao.findOnlyTwo(empNo1, empNo2);
        if (chatroomNo != null) {
            return chatroomDao.findByChatroomNo(chatroomNo);
        } 
        else {
            // 채팅방 번호 생성
            int newChatroomNo = chatroomDao.sequence();
            String newChatroomName = newChatroomNo + "번 채팅방";
            ChatroomDto newChatroomDto = new ChatroomDto(newChatroomNo, newChatroomName);
            chatroomDao.save(newChatroomDto);
            
            // 사원-채팅방 연결 정보 저장
            chatroomDao.connectEmpChatroom(empNo1, newChatroomNo);
            chatroomDao.connectEmpChatroom(empNo2, newChatroomNo);
            
            return newChatroomDto;
        }
    }
}
