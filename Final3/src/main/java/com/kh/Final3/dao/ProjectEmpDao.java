package com.kh.Final3.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.ProjectDto;
import com.kh.Final3.dto.ProjectEmpDto;

@Repository
public class ProjectEmpDao {
	
	@Autowired
	private SqlSession sqlSession;

	
	//문서 조회 (무한스크롤)//
	public List<ProjectEmpDto> selectList() {
		return sqlSession.selectList("projectEmp.list");
	}
	
	
//	public List<ProjectEmpDto> myList(int empNo) {
//		return sqlSession.selectList("projectEmp.myList", empNo);
//	}
//	

	//조회
	public ProjectDto selectOne(int projectNo) {
		return sqlSession.selectOne("projectEmp.find", projectNo);
	}
	

	

	//전체수정
	public boolean edit(ProjectEmpDto projectEmpDto) {
		return sqlSession.update("projectEmp.edit", projectEmpDto) > 0;
	}
	
	
	
	//삭제
	public boolean delete(int empNo) {
		return sqlSession.delete("ProjectEmp.delete", empNo) > 0;
	}

//등록
	public void insertEmp(ProjectEmpDto projectEmpDto) {
		sqlSession.insert("projectEmp.save", projectEmpDto);
		
	}
	


}




