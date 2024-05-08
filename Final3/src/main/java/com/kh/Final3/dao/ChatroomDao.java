package com.kh.Final3.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.ChatroomDto;
import com.kh.Final3.dto.EmpChatroomDto;

@Repository
public class ChatroomDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//조회
	public List<ChatroomDto> selectList(int empNo){
		return sqlSession.selectList("empChatroom.listByEmpNo", empNo);
	}
	
	//채팅방 이름 수정
	public boolean editChatroomName(ChatroomDto chatroomDto) {
		return sqlSession.update("chatroom.edit", chatroomDto) > 0;
	}
	
	
	// 채팅방 번호에 속해있는 사원 조회
	public List<EmpChatroomDto> selectListEmpByChatroom(int chatroomNo){
	    List<EmpChatroomDto> empInChatroomList = sqlSession.selectList("empChatroom.listByRoomNo", chatroomNo);
	    
	    // 각각 이름, 직책 설정
	    for (EmpChatroomDto empChatroomDto : empInChatroomList) {
	    	Map<String, Integer> empInfo = new HashMap<>();
	    	empInfo.put("chatroomNo", chatroomNo);
	    	empInfo.put("empNo", empChatroomDto.getEmpNo());
	        String empName = sqlSession.selectOne("empChatroom.listSetName", empInfo);
	        String empGrade = sqlSession.selectOne("empChatroom.listSetGrade", empInfo);
	        empChatroomDto.setEmpName(empName);
	        empChatroomDto.setEmpGrade(empGrade);
	    }
	    return empInChatroomList;
	}
	
	//위에서 단순히 사원번호만 조회
	public List<EmpChatroomDto> selectListEmpByChatroom2(int chatroomNo){
	    return sqlSession.selectList("empChatroom.listByRoomNo", chatroomNo);
	}
	
	
	//단둘이 있는 채팅방만 조회
	public Integer findOnlyTwo(int empNo1, int empNo2) {
	    Map<String, Integer> emp = new HashMap<>();
	    emp.put("empNo1", empNo1);
	    emp.put("empNo2", empNo2);
	    return sqlSession.selectOne("chatroom.findOnlyTwo", emp);
	}

	//채팅방 시퀀스
   public int sequence() {
	  return sqlSession.selectOne("chatroom.sequence");
   }
   
   // 채팅방 생성
   public void save(ChatroomDto chatroomDto) {
       sqlSession.insert("chatroom.save", chatroomDto);
   }
   
   public void save(int chatroomNo, String chatroomName) {
	   Map<String, Object> info = new HashMap<>();
	    info.put("chatroomNo", chatroomNo);
	    info.put("chatroomName", chatroomName);
       sqlSession.insert("chatroom.save", info);
   }
   
   // 채팅방 번호로 채팅방 조회(?)
   public ChatroomDto findByChatroomNo(int chatroomNo) {
       return sqlSession.selectOne("chatroom.findByChatroomNo", chatroomNo);
   }
   
   //사원-채팅방 연결
   public void connectEmpChatroom(int empNo, int chatroomNo) {
	    Map<String, Integer> info = new HashMap<>();
	    info.put("empNo", empNo);
	    info.put("chatroomNo", chatroomNo);
	    sqlSession.insert("empChatroom.save", info);
	}
   
   public Integer numberOfParticipants(int chatroomNo) {
	   return sqlSession.selectOne("chatroom.numberOfEmpInChatroom", chatroomNo);
   }
   
   //채팅방 이름 설정용
   public String findEmpName(int empNo) {
	   return sqlSession.selectOne("chatroom.findEmpName", empNo);
   }
   
   //채팅방 이름 설정용
   public String findChatroomName(int chatroomNo) {
	   return sqlSession.selectOne("chatroom.findChatroomName", chatroomNo);
   }
   
   public Integer isEmpInChatroom(int empNo, int chatroomNo) {
	    Map<String, Integer> info = new HashMap<>();
	    info.put("empNo", empNo);
	    info.put("chatroomNo", chatroomNo);
	   return sqlSession.selectOne("empChatroom.isEmpInChatroom", info);
   }
   
   

   
	

}
