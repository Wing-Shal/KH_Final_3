package com.kh.Final3.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.ProjectDto;
import com.kh.Final3.dto.ProjectEmpDto;

@Repository
public class ProjectEmpDao {

	@Autowired
	private SqlSession sqlSession;
	
	//프로젝트 참여자 등록
	public void insert(ProjectEmpDto projectEmpDto) {
		sqlSession.insert("projectEmp.save", projectEmpDto);
	}
	
	//프로젝트 Dto 넣기
		public ProjectEmpDto selectOne(int projectNo) {
			return sqlSession.selectOne("project.find", projectNo);
		}
	
}
