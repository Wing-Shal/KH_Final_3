package com.kh.Final3.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.Final3.dao.AdminDao;
import com.kh.Final3.dao.CompanyDao;
import com.kh.Final3.dao.EmpDao;
import com.kh.Final3.dto.AdminDto;
import com.kh.Final3.dto.CompanyDto;
import com.kh.Final3.dto.EmpDto;
import com.kh.Final3.service.JwtService;
import com.kh.Final3.vo.LoginVO;

@CrossOrigin
@RestController
@RequestMapping("/refresh")
public class RefreshRestController {

	@Autowired
	private JwtService jwtService;
	@Autowired
	private EmpDao empDao;
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private AdminDao adminDao;

	@PostMapping("/")
	public ResponseEntity<LoginVO> refresh(@RequestHeader("Authorization") String refreshToken) {
		try {
			LoginVO loginVO = jwtService.parse(refreshToken);
			switch (loginVO.getLoginLevel()) {
			case "운영자": {
				AdminDto adminDto = adminDao.selectOne(loginVO.getLoginId());
				if (adminDto == null) {
					throw new Exception("존재하지 않는 아이디");
				}
				String accessToken = jwtService.createAccessToken(adminDto);
				String newRefreshToken = jwtService.createRefreshToken(adminDto);

				return ResponseEntity.ok().body(LoginVO.builder().loginId(adminDto.getAdminId()).loginLevel("운영자")
						.accessToken(accessToken).refreshToken(newRefreshToken).build());
			}
			case "회사": {
				CompanyDto companyDto = companyDao.selectOne(loginVO.getLoginId());
				if (companyDto == null) {
					throw new Exception("존재하지 않는 아이디");
				}
				String accessToken = jwtService.createAccessToken(companyDto);
				String newRefreshToken = jwtService.createRefreshToken(companyDto);

				return ResponseEntity.ok().body(LoginVO.builder().loginId(companyDto.getCompanyNo()).loginLevel("회사")
						.accessToken(accessToken).refreshToken(newRefreshToken).build());
			}
			default: {
				EmpDto empDto = empDao.selectOne(loginVO.getLoginId());
				if (empDto == null) {
					throw new Exception("존재하지 않는 아이디");
				}
				if (!loginVO.getLoginLevel().equals(empDto.getEmpType())) {
					throw new Exception("정보 불일치");
				}

				String accessToken = jwtService.createAccessToken(empDto);
				String newRefreshToken = jwtService.createRefreshToken(empDto);

				return ResponseEntity.ok()
						.body(LoginVO.builder().loginId(empDto.getEmpNo()).loginLevel(empDto.getEmpType())
								.accessToken(accessToken).refreshToken(newRefreshToken).build());
				}
			}

		} catch (Exception e) {
			return ResponseEntity.status(401).build();
		}
	}
}
