package com.kh.Final3.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.BoardBlindDto;

@Repository
public class BoardBlindDao {

	@Autowired
	private SqlSession sqlSession;

	// 등록(시퀀스)
	public int sequence() {
		return sqlSession.selectOne("boardBlind.sequence");
	}
	public void insert(BoardBlindDto boardBlindDto) {
		sqlSession.insert("boardBlind.save", boardBlindDto);
	}

	// 조회
	public List<BoardBlindDto> selectList() {
		return sqlSession.selectList("boardBlind.list");
	}

	// 상세조회
	public BoardBlindDto selectOne(int blindNo) {
		return sqlSession.selectOne("boardBlind.find", blindNo);
	}

	// 수정
	public boolean editAll(BoardBlindDto boardBlindDto) {
		return sqlSession.update("boardBlind.editAll", boardBlindDto) > 0;
	}

	// 삭제
	public boolean delete(int blindNo) {
		return sqlSession.delete("boardBlind.delete", blindNo) > 0;
	}

}