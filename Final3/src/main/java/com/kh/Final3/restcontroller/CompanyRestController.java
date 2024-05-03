package com.kh.Final3.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.Final3.dao.CompanyDao;
import com.kh.Final3.dto.CompanyDto;
import com.kh.Final3.service.EmailService;
import com.kh.Final3.service.JwtService;
import com.kh.Final3.vo.InputVO;
import com.kh.Final3.vo.LoginVO;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

@CrossOrigin
@RestController
@RequestMapping("/company")
public class CompanyRestController {
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping("/login")
	public ResponseEntity<LoginVO> login(@RequestBody InputVO inputVO) {
		CompanyDto findDto = companyDao.selectOne(Integer.parseInt(inputVO.getId()));
		if(findDto == null) {
			return ResponseEntity.status(404).build();
		}
		
		boolean isValid = findDto.getCompanyPw().equals(inputVO.getPw());

		if(isValid) {
			String accessToken = jwtService.createAccessToken(findDto);
			String refreshToken = jwtService.createRefreshToken(findDto);
			
			return ResponseEntity.ok().body(LoginVO.builder()
					.loginId(findDto.getCompanyNo())
					.loginLevel("회사")
					.accessToken(accessToken)
					.refreshToken(refreshToken)
				.build());//200
		}
		else {
			return ResponseEntity.status(401).build();
		}
	}
	
	@PostMapping("/join")
	public ResponseEntity<CompanyDto> insert(
			@Parameter(description = "회사 생성에 대한 입력값", required = true, schema = @Schema(implementation = CompanyDto.class))
			@RequestBody CompanyDto companyDto) {
		int sequence = companyDao.sequence();
		companyDto.setCompanyNo(sequence);
		companyDao.insert(companyDto);
		emailService.sendVerifyMail(companyDto);
		return ResponseEntity.ok().body(companyDao.selectOne(sequence));
	}
}
