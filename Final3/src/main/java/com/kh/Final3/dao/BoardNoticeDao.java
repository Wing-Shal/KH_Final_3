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
	public List<BoardNoticeDto> selectList(int companyNo){
		return sqlSession.selectList("boardNotice.list", companyNo);
	}
	
	//등록
	public int sequence() { //시퀀스
		return sqlSession.selectOne("boardNotice.sequence");
	}
	public void insert(BoardNoticeDto boardNoticeDto) {
		sqlSession.insert("boardNotice.save", boardNoticeDto);
	}

	//상세조회
	public BoardNoticeDto selectOne(int noticeNo) {
		return sqlSession.selectOne("boardNotice.find", noticeNo);
	}
	
	//수정
	public boolean editUnit(BoardNoticeDto boardNoticeDto) {
		return sqlSession.update("boardNotice.editUnit", boardNoticeDto) > 0;
	}

}
