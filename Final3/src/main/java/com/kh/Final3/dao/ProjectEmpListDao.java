package com.kh.Final3.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.ProjectEmpListDto;

@Repository
public class ProjectEmpListDao {
	@Autowired
	private SqlSession sqlSession;
	
	
	//등록
	public void insert(ProjectEmpListDto projectEmpListDto) {
		sqlSession.insert("projectEmpList.save", projectEmpListDto);
	}

		//전체수정
		public boolean update(ProjectEmpListDto projectEmpListDto) {
			return sqlSession.update("projectEmpList.edit", projectEmpListDto) > 0;
		}
		
		//삭제
		public boolean delete(int projectNo) {
			return sqlSession.delete("projectEmpList.delete", projectNo) > 0;
		}
		
		// 목록
		public List<ProjectEmpListDto> ProjectEmpList(int empNo){
			return sqlSession.selectList("ProjectEmpList.ProjectEmpList",empNo);
		}



}
