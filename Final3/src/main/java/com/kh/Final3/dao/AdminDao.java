package com.kh.Final3.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.AdminDto;

@Repository
public class AdminDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public AdminDto selectOne(int adminId) {
		return sqlSession.selectOne("admin.find", adminId);
	}
}
