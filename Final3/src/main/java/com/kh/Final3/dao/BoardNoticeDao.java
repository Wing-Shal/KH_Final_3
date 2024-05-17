package com.kh.Final3.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	//페이지네이션
	public List<BoardNoticeDto> selectListByPaging(int companyNo, int page, int size, String column, String keyword) {
		int beginRow = (page - 1) * size + 1;
		int endRow = page * size;
		
		Map<String, Object> data = new HashMap<>();
		data.put("companyNo", companyNo);
		data.put("beginRow", beginRow);
		data.put("endRow", endRow);
		data.put("column", column);
		data.put("keyword", keyword);
		
		return sqlSession.selectList("boardNotice.listByPaging", data);
	}
	
	public int noticeCount(int companyNo, String column, String keyword) {
		Map<String, Object> data = new HashMap<>();
		data.put("companyNo", companyNo);
		data.put("column", column);
		data.put("keyword", keyword);
		
		return sqlSession.selectOne("boardNotice.noticeCount", data);
	}

}
