package com.kh.Final3.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.Final3.dao.CompanyDao;
import com.kh.Final3.dto.CompanyDto;
import com.kh.Final3.service.JwtService;
import com.kh.Final3.vo.CompanyLoginVO;

@CrossOrigin
@RestController
@RequestMapping("/company")
public class CompanyRestController {
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/login")
	public ResponseEntity<CompanyLoginVO> login(@RequestBody CompanyDto companyDto) {
		CompanyDto findDto = companyDao.selectOne(companyDto.getCompanyNo());
		if(findDto == null) {
			return ResponseEntity.status(404).build();
		}
		
		boolean isValid = findDto.getCompanyPw().equals(companyDto.getCompanyPw());

		if(isValid) {
			String accessToken = jwtService.createAccessToken(findDto);
			String refreshToken = jwtService.createRefreshToken(findDto);
			
			return ResponseEntity.ok().body(CompanyLoginVO.builder()
					.companyNo(findDto.getCompanyNo())
					.accessToken(accessToken)
					.refreshToken(refreshToken)
				.build());//200
		}
		else {
			return ResponseEntity.status(401).build();
		}
	}
	@PostMapping("/refresh")
	public ResponseEntity<CompanyLoginVO> refresh(@RequestHeader("Authorization") String refreshToken) {
		try {
			CompanyLoginVO loginVO = jwtService.parseCompany(refreshToken);
			CompanyDto companyDto = companyDao.selectOne(loginVO.getCompanyNo());
			if (companyDto == null) {
				throw new Exception("존재하지 않는 아이디");
			}
			String accessToken = jwtService.createAccessToken(companyDto);
			String newRefreshToken = jwtService.createRefreshToken(companyDto);
			
			return ResponseEntity.ok().body(CompanyLoginVO.builder()
					.companyNo(companyDto.getCompanyNo())
					.accessToken(accessToken)
					.refreshToken(newRefreshToken)
				.build());
			
		} catch(Exception e) {
			return ResponseEntity.status(401).build();
		}
	}
}
