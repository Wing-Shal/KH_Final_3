package com.kh.Final3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.Final3.dao.MessageDao;
import com.kh.Final3.vo.MessageInfoVO;

@Service
public class MessageService {
	
    @Autowired
    private MessageDao messageDao;

    public List<MessageInfoVO> retrieveUnreadMessages() {
        return messageDao.getUnreadMessages();
    }
    
    public int countUnreadMessages(int empNo, int chatroomNo) {
        //empNo와 chatroomNo를 이용하여 안 읽은 메시지의 수를 데이터베이스에서 조회
        return messageDao.countUnreadMessages(empNo, chatroomNo);
    }

}
