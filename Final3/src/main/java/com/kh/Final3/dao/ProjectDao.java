package com.kh.Final3.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.ProjectDto;

@Repository
public class ProjectDao {
	
	@Autowired
	private SqlSession sqlSession;

	
	//문서 조회 (무한스크롤)
	public List<ProjectDto> selectList() {
		return sqlSession.selectList("project.list");
	}
	
	//무한 스크롤을 위한 페이징 메소드
		//- 받아야 하는 정보 : 페이지번호, 페이지크기
		//- 반환해야 하는 정보 : 문서 목록, List<ProjectDto>
	public List<ProjectDto> selectListByPaging(int page, int size) {
		int beginRow = page * size - (size-1);
		int endRow = page * size;
		
		Map<String, Object> data = new HashMap<>();
		data.put("beginRow", beginRow);
		data.put("endRow", endRow);
		return sqlSession.selectList("project.listByPaging", data);		
	}
	public List<ProjectDto> myList(int empNo) {
		return sqlSession.selectList("project.myList", empNo);
	}
	
	public int count() {
		return sqlSession.selectOne("project.count");
	}

	//조회
	public ProjectDto selectOne(int projectNo) {
		return sqlSession.selectOne("project.find", projectNo);
	}
	
	//시퀀스
	public int sequence() {
		return sqlSession.selectOne("project.sequence");
	}
	
	//등록
	public void insert(ProjectDto projectDto) {
		sqlSession.insert("project.save", projectDto);
	}

	//전체수정
	public boolean edit(ProjectDto projectDto) {
		return sqlSession.update("project.edit", projectDto) > 0;
	}
	
	
	
	//삭제
	public boolean delete(int projectNo) {
		return sqlSession.delete("project.delete", projectNo) > 0;
	}

}




