package com.kh.Final3.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.vo.AdminCompanyVO;

@Repository
public class AdminCompanyDao {
	@Autowired
	private SqlSession sqlSession;
	
	public List<AdminCompanyVO> CompanyListWithStatus() {
		return sqlSession.selectList("adminCompany.selectCompanyWithStatus");
	}
}
