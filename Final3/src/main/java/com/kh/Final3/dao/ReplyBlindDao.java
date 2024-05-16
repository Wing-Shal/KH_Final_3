package com.kh.Final3.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.BoardBlindDto;
import com.kh.Final3.dto.ReplyBlindDto;

@Repository
public class ReplyBlindDao {

	@Autowired
	private SqlSession sqlSession;
	
//	등록(시퀀스)
	public int sequence() {
		return sqlSession.selectOne("replyBlind.sequence");
	}
	// 등록
		public void insert(ReplyBlindDto replyBlindDto) {
			sqlSession.insert("replyBlind.save", replyBlindDto);
		}
	
	//조회
	public List<ReplyBlindDto> selectList(){
		return sqlSession.selectList("replyBlind.list");
	}
//	상세조회
	public ReplyBlindDto selectOne(int replyBlindNo) {
		return sqlSession.selectOne("replyBlind.find", replyBlindNo);
	}
	
	//수정
	public boolean editAll(ReplyBlindDto replyBlindDto) {
		return sqlSession.update("replyBlind.editAll", replyBlindDto) > 0;
	}
	
	// 삭제
		public boolean delete(int replyBlindNo) {
			return sqlSession.delete("replyBlind.delete", replyBlindNo) > 0;
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
