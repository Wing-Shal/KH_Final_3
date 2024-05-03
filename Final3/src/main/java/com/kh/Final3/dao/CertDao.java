package com.kh.Final3.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.CertDto;


@Repository
public class CertDao {
   @Autowired
   private SqlSession sqlSession;

   public CertDto selectOne(String certEmail) {
      return sqlSession.selectOne("cert.find", certEmail);
   }
   
   public List<CertDto> list() {
	   return sqlSession.selectList("cert.checkValid");
   }
   
   public void insert(CertDto certDto) {
      sqlSession.insert("cert.save", certDto);
   }

   public boolean delete(String certEmail) {
      return sqlSession.delete("cert.delete", certEmail) > 0;
   }
   
   public boolean deleteLegacy(String certEmail) {
	      return sqlSession.delete("cert.delete", certEmail) > 0;
	   }
   
}