package com.kh.Final3.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.Final3.dao.EmpDao;
import com.kh.Final3.dto.EmpDto;
import com.kh.Final3.service.JwtService;
import com.kh.Final3.vo.EmpLoginVO;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("/emp")
public class EmpRestController {
	
	@Autowired
	private EmpDao empDao;
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/login")
	public ResponseEntity<EmpLoginVO> login(@RequestBody EmpDto empDto) {
		EmpDto loginDto = empDao.selectOne(empDto.getEmpNo());
		if(loginDto == null) {
			return ResponseEntity.status(404).build();
		}
		
		boolean isValid = loginDto.getEmpPw().equals(empDto.getEmpPw());

		if(isValid) {
			String accessToken = jwtService.createAccessToken(empDto);
			String refreshToken = jwtService.createRefreshToken(empDto);
			
			return ResponseEntity.ok().body(EmpLoginVO.builder()
					.empNo(loginDto.getEmpNo())
					.accessToken(accessToken)
					.refreshToken(refreshToken)
				.build());//200
		}
		else {
			return ResponseEntity.status(401).build();
		}
	}
	@PostMapping("/refresh")
	public ResponseEntity<EmpLoginVO> refresh(@RequestHeader("Authorization") String refreshToken) {
		try {
			EmpLoginVO loginVO = jwtService.parseEmp(refreshToken);
			EmpDto empDto = empDao.selectOne(loginVO.getEmpNo());
			if (empDto == null) {
				throw new Exception("존재하지 않는 아이디");
			}
			String accessToken = jwtService.createAccessToken(empDto);
			String newRefreshToken = jwtService.createRefreshToken(empDto);
			
			return ResponseEntity.ok().body(EmpLoginVO.builder()
					.empNo(empDto.getEmpNo())
					.accessToken(accessToken)
					.refreshToken(newRefreshToken)
				.build());
			
		} catch(Exception e) {
			return ResponseEntity.status(401).build();
		}
	}
}
