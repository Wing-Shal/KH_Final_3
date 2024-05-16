package com.kh.Final3.restcontroller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.Final3.dao.EmpDao;
import com.kh.Final3.dao.ProjectDao;
import com.kh.Final3.dao.ProjectEmpDao;
import com.kh.Final3.dto.EmpDto;
import com.kh.Final3.dto.ProjectEmpDto;
import com.kh.Final3.service.JwtService;

@CrossOrigin
@RestController
@RequestMapping("/projectEmp")
public class ProjectEmpRestController {

	@Autowired
	private ProjectEmpDao projectEmpDao;
	
	@Autowired
	private EmpDao empDao;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private ProjectDao projectDao;
	
	//프로젝트 참여자 등록
	@PostMapping("/")
	public ResponseEntity<ProjectEmpDto> insertProjectEmp(@RequestBody ProjectEmpDto projectEmpDto){
	    // 프론트엔드에서 전송한 프로젝트 번호 가져오기
	   EmpDto empDto = empDao.selectOne(projectEmpDto.getEmpNo());
	    System.out.println(empDto);
	    // 프로젝트 참여자 등록 로직 실행
	    projectEmpDao.insert(projectEmpDto);
	    
	    // 등록된 프로젝트 참여자 정보 반환
	    return ResponseEntity.ok().body(projectEmpDto);
	}

}
