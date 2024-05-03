package com.kh.Final3.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.BoardBlindDto;
import com.kh.Final3.dto.ProjectDto;

@Repository
public class BoardBlindDao {

	@Autowired
	private SqlSession sqlSession;


	// 조회
	public List<BoardBlindDto> selectList() {
		                            
		return sqlSession.selectList("boardBlind.list");
		
	}
	

	// 페이지 카운트
	public int count() {
		return sqlSession.selectOne("boardBlind.count");
	}

	// 조회
	public BoardBlindDto selectOne(int blindNo) {
		return sqlSession.selectOne("boardBlind.find", blindNo);
	}

	// 시퀀스
	public int sequence() {
		return sqlSession.selectOne("boardBlind.sequence");
	}

	// 등록
	public void insert(BoardBlindDto boardBlindDto) {
		sqlSession.insert("boardBlind.save", boardBlindDto);
	}

	// 전체수정
	public boolean edit(BoardBlindDto boardBlindDto) {
		return sqlSession.update("boardBlind.edit", boardBlindDto) > 0;
	}

	// 삭제
	public boolean delete(int blindNo) {
		return sqlSession.delete("boardBlind.delete", blindNo) > 0;
	}
}