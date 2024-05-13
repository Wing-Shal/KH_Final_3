package com.kh.Final3.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.CalendarDto;

@Repository
public class CalendarDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//캘린더 리스트
	public List<CalendarDto> selectList(){
		 List<CalendarDto> calendarList = sqlSession.selectList("calendar.list");
		 
	    for (CalendarDto calendarDto : calendarList) {
	    	String empName = sqlSession.selectOne("calendar.setEmpName", calendarDto.getCalendarNo());
	    	String empGrade = sqlSession.selectOne("calendar.setEmpGrade", calendarDto.getCalendarNo());
	        calendarDto.setEmpName(empName);
	        calendarDto.setEmpGrade(empGrade);
	    }
		return calendarList;
	}
	
	//하나만보기
	public CalendarDto selectOne(int calendarNo) {
		CalendarDto calendarDto =  sqlSession.selectOne("calendar.find", calendarNo);
		String empName = sqlSession.selectOne("calendar.setEmpName", calendarNo);
		String empGrade = sqlSession.selectOne("calendar.setEmpGrade", calendarNo);
		calendarDto.setEmpName(empName);
		calendarDto.setEmpGrade(empGrade);
		return calendarDto;
	}
	
	//캘린더시퀀스
	public int sequence() {
		return sqlSession.selectOne("calendar.sequence");
	}
	
	//일정 등록
	public void insert(CalendarDto calendarDto) {
		sqlSession.insert("calendar.save", calendarDto);
	}
	
	//일정 수정
	public boolean editAll(CalendarDto calendarDto) {
		return sqlSession.update("calendar.editAll", calendarDto) > 0;
	}
	
	public boolean editUnit(CalendarDto calendarDto) {
		return sqlSession.update("calendar.editUnit", calendarDto) > 0;
	}
	
	//삭제
	public boolean delete(int calendarNo) {
		return sqlSession.delete("calendar.delete", calendarNo) > 0;
	}

}
