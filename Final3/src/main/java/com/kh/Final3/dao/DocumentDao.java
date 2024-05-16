package com.kh.Final3.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.DocumentDto;
import com.kh.Final3.dto.EmpDto;
import com.kh.Final3.dto.ProjectDto;
import com.kh.Final3.vo.ProjectEmpVO;

@Repository
public class DocumentDao {
	@Autowired
	private SqlSession sqlSession;

	
	//문서 조회 (무한스크롤)
	public List<DocumentDto> selectList() {
		return sqlSession.selectList("document.list");
	}
	
	//무한 스크롤을 위한 페이징 메소드//
		//- 받아야 하는 정보 : 페이지번호, 페이지크기
		//- 반환해야 하는 정보 : 문서 목록, List<DocumentDto>
	public List<DocumentDto> selectListByPaging(int page, int size) {
		int beginRow = page * size - (size-1);
		int endRow = page * size;
		
		Map<String, Object> data = new HashMap<>();
		data.put("beginRow", beginRow);
		data.put("endRow", endRow);
		return sqlSession.selectList("document.listByPaging", data);
	
		
	}
	//해당 프로젝트에 맞는 문서 조회
	public List<DocumentDto> docuList(int projectNo){
		return sqlSession.selectList("document.docuList",projectNo);
	}
	public int count() {
		return sqlSession.selectOne("document.count");
	}

	//조회
	public DocumentDto selectOne(int documentNo) {
		return sqlSession.selectOne("document.find", documentNo);
	}
	
	//프로젝트 Dto 넣기
	public ProjectDto selectOne1(int projectNo) {
		return sqlSession.selectOne("project.find2", projectNo);
	}
	
	//시퀀스
	public int sequence() {
		return sqlSession.selectOne("document.sequence");
	}
	
	//등록
	public void insert(DocumentDto documentDto) {
		System.out.println(documentDto);
		sqlSession.insert("document.save", documentDto);
	}

	//전체수정
	public boolean edit(DocumentDto documentDto) {
		return sqlSession.update("document.edit", documentDto) > 0;
	}
	
	
	//삭제
	public boolean delete(int documentNo) {
		return sqlSession.delete("document.delete", documentNo) > 0;
	}

	//통합검색
	public List<DocumentDto>searchDocuments(String keyword){
		return sqlSession.selectList("document.searchDocuments",keyword);
	}

	public List<ProjectEmpVO> getCompanyEmployeesInfo(int companyNo) {
	    return sqlSession.selectList("document.detailList", companyNo);
	}
	
	//같은 회사의 사원리스트 불러오기
	
}

