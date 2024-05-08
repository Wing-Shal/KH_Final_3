package com.kh.Final3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.Final3.dao.ChatroomDao;
import com.kh.Final3.dto.ChatroomDto;
import com.kh.Final3.dto.EmpChatroomDto;

@Service
public class ChatroomService {
    
    @Autowired
    private ChatroomDao chatroomDao;

    //채팅방 찾기 또는 생성
    public ChatroomDto findOrCreateChatroom(int empNo1, int empNo2) {
    	//여기서 empNo1은 로그인한 아이디, empNo2는 채팅하기 누른 상대
        Integer chatroomNo = chatroomDao.findOnlyTwo(empNo1, empNo2);
        if (chatroomNo != null) {
            return chatroomDao.findByChatroomNo(chatroomNo);
        } 
        else {
            //채팅방 번호 생성
            int newChatroomNo = chatroomDao.sequence();
            
            //사원 이름 찾기
            String empName1 = chatroomDao.findEmpName(empNo1);
            String empName2 = chatroomDao.findEmpName(empNo2);
            String newChatroomName = empName1 + ", " + empName2;
            ChatroomDto newChatroomDto = new ChatroomDto(newChatroomNo, newChatroomName);
            chatroomDao.save(newChatroomDto);
            
            //사원-채팅방 연결 정보 저장
            chatroomDao.connectEmpChatroom(empNo1, newChatroomNo);
            chatroomDao.connectEmpChatroom(empNo2, newChatroomNo);
            
            return newChatroomDto;
        }
    }
    
    //그룹 초대
    public ChatroomDto inviteEmp(int chatroomNo, int empNo) {
    	//여기서 empNo는 초대된 상대
    	
        //해당 사원이 이미 채팅방에 있는지 확인
        Integer isEmpInChatroom = chatroomDao.isEmpInChatroom(empNo, chatroomNo);

        if (isEmpInChatroom >= 1) {
            throw new IllegalArgumentException("이미 채팅방에 있음 ㅋㅋ");
        }
    	
    	//해당 채팅방에 몇명 있는지
    	Integer numberOfParticipants = chatroomDao.numberOfParticipants(chatroomNo);
    	//해당 채팅방에 누구누구 있는지
    	List<EmpChatroomDto> empchatroomDto = chatroomDao.selectListEmpByChatroom2(chatroomNo);
    	List<Integer> originEmpNos = new ArrayList<>();
        for (EmpChatroomDto dto : empchatroomDto) {
        	originEmpNos.add(dto.getEmpNo());
        }
    	if(numberOfParticipants <= 2) { //만약 인원이 2명이하면 새로운 채팅방 개설
            int newChatroomNo = chatroomDao.sequence();
            
            //참가자 이름으로 채팅방 이름 생성
            StringBuilder chatroomNameBuilder = new StringBuilder();
            for (Integer empNos : originEmpNos) {
                String empName = chatroomDao.findEmpName(empNos);
                if (chatroomNameBuilder.length() > 0) chatroomNameBuilder.append(", ");
                chatroomNameBuilder.append(empName);
            }
            String empName2 = chatroomDao.findEmpName(empNo);
            
            String newChatroomName = chatroomNameBuilder.toString() + ", " + empName2;
            
            ChatroomDto newChatroomDto = new ChatroomDto(newChatroomNo, newChatroomName);
            chatroomDao.save(newChatroomDto);
            
            //기존 사원-채팅방 연결 정보 저장
            for(Integer existingEmpNo : originEmpNos) {
            	chatroomDao.connectEmpChatroom(existingEmpNo, newChatroomNo);
            }
            //초대된 사원-채팅방 연결 정보 저장
            chatroomDao.connectEmpChatroom(empNo, newChatroomNo);
            
            return newChatroomDto;
    	}
    	else { //만약 3명 이상이면 그 채팅방에 그냥 해당 사원 넣기
    		chatroomDao.connectEmpChatroom(empNo, chatroomNo);
    		
    		//사원 이름 찾기
//            String empName = chatroomDao.findEmpName(empNo);
    		String chatroomName = chatroomDao.findChatroomName(chatroomNo);
    		
    		ChatroomDto inviteChatroomDto = new ChatroomDto(chatroomNo, chatroomName);
    		
            return inviteChatroomDto;
    	}
    }
}
