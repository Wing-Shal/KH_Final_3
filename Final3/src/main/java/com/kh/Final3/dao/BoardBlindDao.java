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


//	// 조회
//	public List<BoardBlindDto> selectList() {
//		                            
//		return sqlSession.selectList("boardBlind.list");
//		
//	}
	
	 // 게시글 목록 가져오기
    public List<BoardBlindDto> selectBlindList() {
        return sqlSession.selectList("boardBlind.selectBlindList");
    }

	// 페이지 카운트
	public int count() {
		return sqlSession.selectOne("boardBlind.count");
	}

	// 상세조회
	public List<BoardBlindDto> find(int blindNo){
		return sqlSession.selectList("boardBlind.find",blindNo);
	}
	
	
	
	
	public BoardBlindDto selectOne(int blindNo) {
		return sqlSession.selectOne("boardBlind.find", blindNo);
	}
	
//	public List<BoardBlindDto> docuList(int blindEmpNo){
//        return sqlSession.selectList("boardBlind.docuList",blindEmpNo);
//    }

	// 시퀀스
	public int sequence() {
		return sqlSession.selectOne("boardBlind.sequence");
	}

	// 등록
	public void insert(BoardBlindDto boardBlindDto) {
		sqlSession.insert("boardBlind.save", boardBlindDto);
	}

	// 전체수정 dd
	public boolean edit(BoardBlindDto boardBlindDto) {
		return sqlSession.update("boardBlind.edit", boardBlindDto) > 0;
	}

	// 삭제
	public boolean delete(int blindNo) {
		return sqlSession.delete("boardBlind.delete", blindNo) > 0;
	}
	
	// 토큰을 이용하여 사용자의 사원 번호와 회사 번호를 가져오는 메서드 추가
    public int selectCompanyNoByToken(String token) {
        return sqlSession.selectOne("boardBlind.selectCompanyNoByToken", token);
    }

    // 회사 번호를 이용하여 회사 이름을 가져오는 메서드 추가
    public String selectCompanyNameByCompanyNo(int companyNo) {
        return sqlSession.selectOne("boardBlind.selectCompanyNameByCompanyNo", companyNo);
    }
	
	
}