package com.kh.Final3.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.DeptDto;
import com.kh.Final3.dto.EmpDto;
import com.kh.Final3.vo.EmpInfoVO;

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
   public List<EmpDto> selectListByDept(int deptNo) {
     return sqlSession.selectList("emp.listByDept", deptNo);
  }
   public List<EmpDto> selectListByGrade(int gradeNo) {
	     return sqlSession.selectList("emp.listByGrade", gradeNo);
	  }
   
   //회사-사원 리스트
   public List<EmpInfoVO> detailList(int companyNo) {
	   return sqlSession.selectList("emp.detailList", companyNo);
   }
   //회사-사원 리스트(페이징)
   public List<EmpInfoVO> detailListByPaging(int page, int size, int companyNo) {
	   int beginRow = page * size - (size-1);
		int endRow = page * size;
		
		Map<String, Object> data = new HashMap<>();
		data.put("beginRow", beginRow);
		data.put("endRow", endRow);
		data.put("companyNo", companyNo);
		return sqlSession.selectList("emp.detailListByPaging", data);
   }
   //회사-사원 리스트(페이징, 검색)
   public List<EmpInfoVO> detailListByPagingAndSearch(int page, int size, int companyNo, String search) {
	   int beginRow = page * size - (size-1);
		int endRow = page * size;
		
		Map<String, Object> data = new HashMap<>();
		data.put("beginRow", beginRow);
		data.put("endRow", endRow);
		data.put("companyNo", companyNo);
		data.put("search", search);
		return sqlSession.selectList("emp.detailListByPagingAndSearch", data);	
   }
   
   public int count(int companyNo) {
	   return sqlSession.selectOne("emp.count", companyNo);
   }
   public int countWithSearch(int companyNo, String search) {
	   Map<String, Object> data = new HashMap<>();
	   data.put("companyNo", companyNo);
	   data.put("search", search);
	   return sqlSession.selectOne("emp.countWithSearch", data);
   }
   
   public EmpDto selectOne(int empNo) {
	   EmpDto empDto = sqlSession.selectOne("emp.find", empNo);

	  //부서 이름 설정
	   String empDept = sqlSession.selectOne("emp.setDeptName", empNo);
	   if(empDept == null) return empDto;
	   
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