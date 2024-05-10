package com.kh.Final3.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.Final3.dao.CompanyDao;
import com.kh.Final3.dao.DeptDao;
import com.kh.Final3.dao.EmpDao;
import com.kh.Final3.dao.GradeDao;
import com.kh.Final3.dao.PaymentDao;
import com.kh.Final3.dto.CompanyDto;
import com.kh.Final3.dto.DeptDto;
import com.kh.Final3.dto.EmpDto;
import com.kh.Final3.dto.GradeDto;
import com.kh.Final3.service.EmailService;
import com.kh.Final3.service.JwtService;
import com.kh.Final3.vo.EmpInfoVO;
import com.kh.Final3.vo.EmpInputVO;
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
	
	@Autowired
	private PaymentDao paymentDao;
	
	@Autowired
	private EmpDao empDao;
	
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
					.isPaid(paymentDao.isPaid(findDto.getCompanyNo()))
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
	
	@GetMapping("/emp")
	public ResponseEntity<List<EmpInfoVO>> empList(@RequestHeader("Authorization") String token) {
		LoginVO loginVO = jwtService.parse(token);
		int companyNo = loginVO.getLoginId();
		
		List<EmpInfoVO> empList = empDao.detailList(companyNo);
		return ResponseEntity.ok().body(empList);
	}
	@GetMapping("/gradeList")
	public ResponseEntity<List<String>> gradeList(@RequestHeader("Authorization") String token) {
		System.out.println(token);
		LoginVO loginVO = jwtService.parse(token);
		int companyNo = loginVO.getLoginId();
		return ResponseEntity.ok().body(companyDao.gradeList(companyNo));
	}
	@GetMapping("/deptList")
	public ResponseEntity<List<String>> deptList(@RequestHeader("Authorization") String token) {
		LoginVO loginVO = jwtService.parse(token);
		int companyNo = loginVO.getLoginId();
		
		return ResponseEntity.ok().body(companyDao.deptList(companyNo));
	}
	
	@Autowired
	private DeptDao deptDao;
	@Autowired
	private GradeDao gradeDao;
	
	@PatchMapping("/emp")
	public boolean updateEmp(@RequestBody EmpInfoVO empInfoVO) {
		int companyNo = empInfoVO.getCompanyNo();
		String gradeName = empInfoVO.getGradeName();
		String deptName = empInfoVO.getDeptName();
		
		DeptDto deptDto = new DeptDto();
		deptDto.setCompanyNo(companyNo);
		deptDto.setDeptName(deptName);
		int deptNo = deptDao.findDeptNo(deptDto);
		
		GradeDto gradeDto = new GradeDto();
		gradeDto.setCompanyNo(companyNo);
		gradeDto.setGradeName(gradeName);
		int gradeNo = gradeDao.findGradeNo(gradeDto);
		
		EmpDto empDto = new EmpDto();
		empDto.setGradeNo(gradeNo);
		empDto.setDeptNo(deptNo);
		empDto.setEmpNo(empInfoVO.getEmpNo());
		return empDao.editUnit(empDto);
	}
	
	@PostMapping("/emp")
	public ResponseEntity<?> addEmp(@RequestBody List<EmpInputVO> empInputVOList, @RequestHeader("Authorization") String token) {
		LoginVO loginVO = jwtService.parse(token);
		int companyNo = loginVO.getLoginId();
		
		for(EmpInputVO empInputVO : empInputVOList) {
			String gradeName = empInputVO.getGradeName();
			String deptName = empInputVO.getDeptName();
			
			DeptDto deptDto = new DeptDto();
			deptDto.setCompanyNo(companyNo);
			deptDto.setDeptName(deptName);
			int deptNo = deptDao.findDeptNo(deptDto);
			
			GradeDto gradeDto = new GradeDto();
			gradeDto.setCompanyNo(companyNo);
			gradeDto.setGradeName(gradeName);
			int gradeNo = gradeDao.findGradeNo(gradeDto);
			
			EmpDto empDto = new EmpDto();
			int sequence = empDao.sequence();
			empDto.setEmpNo(sequence);
			empDto.setEmpPw(String.valueOf(sequence));
			empDto.setEmpName(empInputVO.getEmpName());
			empDto.setEmpContact(empInputVO.getEmpContact());
			empDto.setEmpEmail(empInputVO.getEmpEmail());
			empDto.setEmpType(empInputVO.getEmpType());
			empDto.setGradeNo(gradeNo);
			empDto.setCompanyNo(companyNo);
			empDto.setDeptNo(deptNo);
			empDto.setEmpStatus("재직");
			empDao.insert(empDto);
		}
		
		return ResponseEntity.ok().build();
	}
}
