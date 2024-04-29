package com.kh.Final3.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.BoardNoticeDto;
import com.kh.Final3.dto.ReplyDocumentDto;

@Repository
public class ReplyDocumentDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//등록
	
	public int sequence() {
		return sqlSession.selectOne("replyDocument.sequence");
	}
	
	public void insert(ReplyDocumentDto replyDocumentDto) {
		sqlSession.insert("replyDocument.save",replyDocumentDto);
	}

	//조회
		public List<ReplyDocumentDto> selectList(){
			return sqlSession.selectList("replyDocument.list");
		}
		
		//삭제
		public boolean delete(int replyDocumentNo) {
			return sqlSession.delete("replyDocument.delete", replyDocumentNo) > 0;
		}

		public ReplyDocumentDto selectOne(int replyDocumentNo) {
			
			return sqlSession.selectOne("replyDocument.find",replyDocumentNo);
		}
	
}
