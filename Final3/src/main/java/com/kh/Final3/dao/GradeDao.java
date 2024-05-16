package com.kh.Final3.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.DeptDto;
import com.kh.Final3.dto.GradeDto;

@Repository
public class GradeDao {
	@Autowired
	private SqlSession sqlSession;

	public int sequence() {
		return sqlSession.selectOne("grade.sequence");
	}
	
	public void insert(GradeDto gradeDto) {
		sqlSession.insert("grade.add", gradeDto);
	}

	public int findGradeNo(GradeDto gradeDto) {
		return sqlSession.selectOne("grade.find", gradeDto);
	}

	public List<GradeDto> selectList(int companyNo) {
		return sqlSession.selectList("grade.listByCompany", companyNo);
	}
	
	public String selectNameByNo(int gradeNo) {
	   return sqlSession.selectOne("grade.selectNameByNo", gradeNo);
   }
	public boolean editAll(GradeDto gradeDto) {
      return sqlSession.update("grade.editAll", gradeDto) > 0;
   }
	
	public boolean delete(int gradeNo) {
		return sqlSession.delete("grade.delete", gradeNo) > 0;
	}

}
