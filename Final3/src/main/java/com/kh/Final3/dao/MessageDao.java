package com.kh.Final3.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.MessageDto;

@Repository
public class MessageDao {

	@Autowired
	private SqlSession sqlSession;
	
//	public MessageDto insert(MessageDto messageDto) {
//		int sequence = sqlSession.selectOne("message.sequence");
//		messageDto.setMessageNo(sequence);
//		sqlSession.insert("message.save", messageDto);
//		return sqlSession.selectOne("message.find", sequence);
//	}
	
	//조회
	public List<MessageDto> selectList(int chatroomNo){
		return sqlSession.selectList("message.list", chatroomNo);
	}
	
}
