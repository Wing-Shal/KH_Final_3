package com.kh.Final3.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.ChatroomDto;

@Repository
public class ChatroomDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//조회
	public List<ChatroomDto> selectList(int empNo){
		return sqlSession.selectList("empChatroom.listByEmpNo", empNo);
	}

}
