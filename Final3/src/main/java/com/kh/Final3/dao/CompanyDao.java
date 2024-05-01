package com.kh.Final3.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.CompanyDto;
import com.kh.Final3.vo.CompanySearchVO;

@Repository
public class CompanyDao {
   @Autowired
   private SqlSession sqlSession;

   public List<CompanyDto> list() {
	   return sqlSession.selectList("company.list");
   }
   
   public List<CompanyDto> selectList(CompanySearchVO companySearchVO) {
      return sqlSession.selectList("company.listOrSearch", companySearchVO);
   }

   public CompanyDto selectOne(int companyNo) {
      return sqlSession.selectOne("company.find", companyNo);
   }
   
   public int sequence() {
      return sqlSession.selectOne("company.sequence");
   }
   public void insert(CompanyDto companyDto) {
      sqlSession.insert("company.save", companyDto);
   }

   public boolean editAll(CompanyDto companyDto) {
      return sqlSession.update("company.editAll", companyDto) > 0;
   }
   public boolean editUnit(CompanyDto companyDto) {
      return sqlSession.update("company.editUnit", companyDto) > 0;
   }
   public boolean approveCompany(int companyNo) {
	   return sqlSession.update("company.approveCompany", companyNo) > 0;
   }

   public boolean delete(int companyNo) {
      return sqlSession.delete("company.delete", companyNo) > 0;
   }
   
}