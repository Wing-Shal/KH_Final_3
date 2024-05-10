package com.kh.Final3.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.GradeDto;

@Repository
public class GradeDao {
	@Autowired
	   private SqlSession sqlSession;


	   public int findGradeNo(GradeDto gradeDto) {
	      return sqlSession.selectOne("grade.find", gradeDto);
	   }
}
