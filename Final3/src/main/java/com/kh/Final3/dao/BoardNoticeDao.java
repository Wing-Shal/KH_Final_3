package com.kh.Final3.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.BoardNoticeDto;

@Repository
public class BoardNoticeDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//조회
	public List<BoardNoticeDto> selectList(){
		return sqlSession.selectList("boardNotice.list");
	}
	
	//등록
	public int sequence() { //시퀀스
		return sqlSession.selectOne("boardNotice.sequence");
	}
	public void insert(BoardNoticeDto boardNoticeDto) {
		sqlSession.insert("boardNotice.save", boardNoticeDto);
	}
	
	//삭제
	public boolean delete(int noticeNo) {
		return sqlSession.delete("boardNotice.delete", noticeNo) > 0;
	}

	//상세조회
	public BoardNoticeDto selectOne(int noticeNo) {
		return sqlSession.selectOne("boardNotice.find", noticeNo);
	}

}
