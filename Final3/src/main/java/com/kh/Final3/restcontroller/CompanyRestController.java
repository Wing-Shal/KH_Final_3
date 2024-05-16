package com.kh.Final3.restcontroller;

import java.io.IOException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.Final3.dao.CompanyDao;
import com.kh.Final3.dao.DeptDao;
import com.kh.Final3.dao.EmpDao;
import com.kh.Final3.dao.GradeDao;
import com.kh.Final3.dao.PaymentDao;
import com.kh.Final3.dto.CompanyDto;
import com.kh.Final3.dto.DeptDto;
import com.kh.Final3.dto.EmpDto;
import com.kh.Final3.dto.GradeDto;
import com.kh.Final3.service.AttachService;
import com.kh.Final3.service.EmailService;
import com.kh.Final3.service.JwtService;
import com.kh.Final3.vo.EmpDataVO;
import com.kh.Final3.vo.EmpInfoVO;
import com.kh.Final3.vo.EmpInputVO;
import com.kh.Final3.vo.InputVO;
import com.kh.Final3.vo.JoinVO;
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
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping("/join")
	public ResponseEntity<?> insert(
			@Parameter(description = "회사 생성에 대한 입력값", required = true, schema = @Schema(implementation = CompanyDto.class))
			@RequestBody JoinVO joinVO) {
		CompanyDto companyDto = modelMapper.map(joinVO, CompanyDto.class);
		int sequence = companyDao.sequence();
		companyDto.setCompanyNo(sequence);
		companyDao.insert(companyDto);
		
		List<DeptDto> depts = joinVO.getDepts();
		for(DeptDto deptDto : depts) {
			deptDto.setCompanyNo(sequence);
			
			int deptNo = deptDao.sequence();
			deptDto.setDeptNo(deptNo);
			
			deptDao.insert(deptDto);
		};
		
		List<GradeDto> grades = joinVO.getGrades();
		for(GradeDto gradeDto : grades) {
			gradeDto.setCompanyNo(sequence);
			
			int gradeNo = gradeDao.sequence();
			gradeDto.setGradeNo(gradeNo);
			
			gradeDao.insert(gradeDto);
		}
		
		emailService.sendVerifyMail(companyDto);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/")
    public ResponseEntity<CompanyDto> find(@RequestHeader("Authorization") String token){
         LoginVO loginVO = jwtService.parse(token);
         int companyNo = loginVO.getLoginId();
         CompanyDto companyDto = companyDao.selectOne(companyNo);
         
         if(companyDto == null) return ResponseEntity.notFound().build();
         return ResponseEntity.ok().body(companyDto);
    }
    
     @Autowired
     private AttachService attachService;
     
      //첨부파일수정
      @PostMapping("/upload/{companyNo}")
      public ResponseEntity<?> insertEdit(@PathVariable int companyNo, @RequestParam("attach") MultipartFile attach) 
              throws IllegalStateException, IOException {
          if (!attach.isEmpty()) {
              boolean isFirst = false;
              try {
                  int attachNo = companyDao.findAttach(companyNo);
                  attachService.remove(attachNo);
                  int editAttachNo = attachService.save(attach);
                  companyDao.connect(companyNo, editAttachNo);
              } catch (Exception e) {
                  isFirst = true;
              } 
              if (isFirst) {
                  int inputAttachNo = attachService.save(attach);
                  companyDao.connect(companyNo, inputAttachNo);
              }
              return ResponseEntity.ok().build();
          }
          return ResponseEntity.status(404).build();
      }
      
      @PatchMapping("/edit")
      public ResponseEntity<CompanyDto> editUnit(@RequestBody CompanyDto companyDto) {
          boolean result = companyDao.editUnit(companyDto);
          if(result == false) return ResponseEntity.notFound().build();
          return ResponseEntity.ok().body(companyDao.selectOne(companyDto.getCompanyNo()));//수정 완료된 결과를 조회하여 반환
      }
	
	
	//페이징 없는 리스트
//	@GetMapping("/emp")
//	public ResponseEntity<List<EmpInfoVO>> empList(@RequestHeader("Authorization") String token) {
//		LoginVO loginVO = jwtService.parse(token);
//		int companyNo = loginVO.getLoginId();
//		
//		List<EmpInfoVO> empList = empDao.detailList(companyNo);
//		return ResponseEntity.ok().body(empList);
//	}
	//사원 목록(페이징)
//	@GetMapping("/emp/page/{page}/size/{size}")
//	public ResponseEntity<EmpDataVO> list(
//		@PathVariable int page, 
//		@PathVariable int size,
//		@RequestHeader("Authorization") String token) {
//		LoginVO loginVO = jwtService.parse(token);
//		int companyNo = loginVO.getLoginId();
//		List<EmpInfoVO> list = empDao.detailListByPaging(page, size, companyNo);
//		int count = empDao.count(companyNo);
//		int endRow = page * size;
//		boolean last = endRow >= count;
//		EmpDataVO empDataVO = EmpDataVO.builder()
//													.list(list)
//													.count(count)
//													.last(last)
//												.build();
//		return ResponseEntity.ok().body(empDataVO);
//	}
	//사원 목록(페이징, 검색)
	@GetMapping("/emp/page/{page}/size/{size}")
	public ResponseEntity<EmpDataVO> list(
		@PathVariable int page, 
		@PathVariable int size,
		@RequestParam(required = false) String search,
		@RequestHeader("Authorization") String token) {
		LoginVO loginVO = jwtService.parse(token);
		int companyNo = loginVO.getLoginId();
		List<EmpInfoVO> list = empDao.detailListByPagingAndSearch(page, size, companyNo, search);
		int count = empDao.countWithSearch(companyNo, search);
		int endRow = page * size;
		boolean last = endRow >= count;
		EmpDataVO empDataVO = EmpDataVO.builder()
													.list(list)
													.count(count)
													.last(last)
												.build();
		return ResponseEntity.ok().body(empDataVO);
	}
	
	@GetMapping("/gradeList")
	public ResponseEntity<List<String>> gradeList(@RequestHeader("Authorization") String token) {
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
	
	@GetMapping("/dept")
	public ResponseEntity<List<DeptDto>> deptListByCompany(@RequestHeader("Authorization") String token) {
		LoginVO loginVO = jwtService.parse(token);
		int companyNo = loginVO.getLoginId();
		
		return ResponseEntity.ok().body(deptDao.selectList(companyNo));
	}
	
	@PostMapping("/dept")
	public ResponseEntity<?> updateDept(@RequestBody List<DeptDto> deptList) {		
		for(DeptDto deptDto : deptList) {
			int deptNo = deptDto.getDeptNo();
			if(deptNo == 0) { //새로 추가된 부서라면
				deptDto.setDeptNo(deptDao.sequence());
				deptDao.insert(deptDto);
			} else { //존재하던 부서
				String currentName = deptDao.selectNameByNo(deptNo);
				if(!currentName.equals(deptDto.getDeptName())) { //변경된 사항이 있다면
					deptDao.editAll(deptDto);
				}
			}
		}
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/dept/hasEmp/{deptNo}")
	public ResponseEntity<List<EmpDto>> hasDeptEmp(@PathVariable int deptNo) {
		return ResponseEntity.ok().body(empDao.selectListByDept(deptNo));
	}
	@DeleteMapping("/dept/{deptNo}")
	public ResponseEntity<?> deleteDept(@PathVariable int deptNo) {
		return ResponseEntity.ok().body(deptDao.delete(deptNo));
	}
	
	
	
	@GetMapping("/grade")
	public ResponseEntity<List<GradeDto>> gradeListByCompany(@RequestHeader("Authorization") String token) {
		LoginVO loginVO = jwtService.parse(token);
		int companyNo = loginVO.getLoginId();
		
		return ResponseEntity.ok().body(gradeDao.selectList(companyNo));
	}
	
	@PostMapping("/grade")
	public ResponseEntity<?> updateGrade(@RequestBody List<GradeDto> gradeList) {		
		for(GradeDto gradeDto : gradeList) {
			int gradeNo = gradeDto.getGradeNo();
			if(gradeNo == 0) { //새로 추가된 부서라면
				gradeDto.setGradeNo(gradeDao.sequence());
				gradeDao.insert(gradeDto);
			} else { //존재하던 부서
				String currentName = gradeDao.selectNameByNo(gradeNo);
				if(!currentName.equals(gradeDto.getGradeName())) { //변경된 사항이 있다면
					gradeDao.editAll(gradeDto);
				}
			}
		}
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/grade/hasEmp/{gradeNo}")
	public ResponseEntity<List<EmpDto>> hasGradeEmp(@PathVariable int gradeNo) {
		return ResponseEntity.ok().body(empDao.selectListByGrade(gradeNo));
	}
	@DeleteMapping("/grade/{gradeNo}")
	public ResponseEntity<?> deleteGrade(@PathVariable int gradeNo) {
		return ResponseEntity.ok().body(gradeDao.delete(gradeNo));
	}
}
