package com.kh.Final3.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.AttachDto;

@Repository
public class AttachDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int sequence () {
		return sqlSession.selectOne("attach.sequence");
	}
	
	public AttachDto find (int attachNo) {
		return sqlSession.selectOne("attach.find", attachNo);
	}
	
	public void insert (AttachDto attachDto) {
		sqlSession.insert("attach.insert", attachDto);
	}
	
	public void insertEmp (AttachDto attachDto) {
		sqlSession.insert("attach.insertEmp", attachDto);
	}
	
	public boolean delete (int attachNo) {
		return sqlSession.delete("attach.delete", attachNo) > 0;
	}
	
	public AttachDto findEmpAttach (int empNo) {
		return sqlSession.selectOne("attach.findEmpAttach", empNo);
	}
	

	


}