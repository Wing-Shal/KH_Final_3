package com.kh.Final3.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.DeptDto;
import com.kh.Final3.dto.EmpDto;

@Repository
public class EmpDao {
   @Autowired
   private SqlSession sqlSession;

   public List<EmpDto> selectListByCompany(int companyNo) {
	   List<EmpDto> empList = sqlSession.selectList("emp.listByCompany", companyNo);
	   
	   //리스트 뽑을 때 직책 설정
	   for(EmpDto empDto : empList) {
		   String empGrade = sqlSession.selectOne("emp.listSetGrade", empDto.getEmpNo());
		   String empDept = sqlSession.selectOne("emp.setDeptName", empDto.getEmpNo());
		   empDto.setEmpGrade(empGrade);
		   empDto.setEmpDept(empDept);
	   }
         return empList;
      }
   public List<EmpDto> selectListByDept(DeptDto deptDto) {
         return sqlSession.selectList("emp.listByDept", deptDto);
      }

   public EmpDto selectOne(int empNo) {
	   EmpDto empDto = sqlSession.selectOne("emp.find", empNo);

	  //부서 이름 설정
	   String empDept = sqlSession.selectOne("emp.setDeptName", empNo);
	   empDto.setEmpDept(empDept);
	   
      return empDto;
   }
   
   public int sequence() {
      return sqlSession.selectOne("emp.sequence");
   }
   public void insert(EmpDto empDto) {
      sqlSession.insert("emp.save", empDto);
   }

   public boolean editAll(EmpDto empDto) {
      return sqlSession.update("emp.editAll", empDto) > 0;
   }
   public boolean editUnit(EmpDto empDto) {
      return sqlSession.update("emp.editUnit", empDto) > 0;
   }

   public boolean delete(int empNo) {
      return sqlSession.delete("emp.delete", empNo) > 0;
   }
   
   
   public EmpDto find (int empNo) {
		return sqlSession.selectOne("emp.find", empNo);
	}
   
   public void connect (int empNo, int attachNo) {
		Map<String, Object> data = new HashMap<>();
		data.put("empNo", empNo);
		data.put("attachNo", attachNo);
		sqlSession.insert("emp.connect", data);
	}
	
	public int findAttach (int empNo) {
		return sqlSession.selectOne("emp.findAttach", empNo);
	}

}