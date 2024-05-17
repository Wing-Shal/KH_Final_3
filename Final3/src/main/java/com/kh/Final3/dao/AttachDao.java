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
      
   public boolean delete (int attachNo) {
      return sqlSession.delete("attach.delete", attachNo) > 0;
   }
   
   public int findByEmpNo(int empNo) {
	   Integer result =  sqlSession.selectOne("attach.findByEmpNo", empNo);
	   return result != null ? result : 0; // 결과가 null일 경우 0을 반환
   }
   
   public int findByCompanyNo(int companyNo) {
	   Integer result = sqlSession.selectOne("attach.findByCompanyNo", companyNo);
	    return result != null ? result : 0; // 결과가 null일 경우 0을 반환
   }
   
 
}