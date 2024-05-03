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
	
	//채팅방 번호에 속해있는 사원 조회
	public List<EmpChatroomDto> selectListEmp(int chatroomNo){
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
   
   // 채팅방 번호로 채팅방 조회
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
   
   
	

}
