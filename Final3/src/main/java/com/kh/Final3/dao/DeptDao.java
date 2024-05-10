package com.kh.Final3.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.DeptDto;

@Repository
public class DeptDao {
   @Autowired
   private SqlSession sqlSession;

   public List<DeptDto> selectList() {
      return sqlSession.selectList("dept.list");
   }

   public int findDeptNo(DeptDto deptDto) {
      return sqlSession.selectOne("dept.find", deptDto);
   }
   
   public int sequence() {
      return sqlSession.selectOne("dept.sequence");
   }
   public void insert(DeptDto deptDto) {
      sqlSession.insert("dept.save", deptDto);
   }

   public boolean editAll(DeptDto deptDto) {
      return sqlSession.update("dept.editAll", deptDto) > 0;
   }
   public boolean editUnit(DeptDto deptDto) {
      return sqlSession.update("dept.editUnit", deptDto) > 0;
   }

   public boolean delete(int deptNo) {
      return sqlSession.delete("dept.delete", deptNo) > 0;
   }
   
}