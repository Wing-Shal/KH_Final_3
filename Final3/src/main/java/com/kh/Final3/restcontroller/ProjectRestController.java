package com.kh.Final3.restcontroller;

import java.util.List;

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

import com.kh.Final3.dao.DocumentDao;
import com.kh.Final3.dao.EmpDao;
import com.kh.Final3.dao.ProjectDao;
import com.kh.Final3.dto.DocumentDto;
import com.kh.Final3.dto.EmpDto;
import com.kh.Final3.dto.ProjectDto;
import com.kh.Final3.service.JwtService;
import com.kh.Final3.vo.LoginVO;
import com.kh.Final3.vo.ProjectDataVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@CrossOrigin
@RestController
@RequestMapping("/project")
public class ProjectRestController {

	@Autowired
	private ProjectDao projectDao;
	@Autowired
    private JwtService jwtService;
	@Autowired
	private EmpDao empDao;
	@Autowired
	private DocumentDao documentDao;
	
	// 문서용 설정 추가
	@Operation(description = "문서 목록 조회", responses = {
			@ApiResponse(responseCode = "200", description = "조회 성공", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProjectDto.class))) }),
			@ApiResponse(responseCode = "500", description = "서버 오류", content = {
					@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class), examples = @ExampleObject("server error")) }) })

	// 목록조회 (무한스크롤)
	@GetMapping("/")
	public List<ProjectDto> list() {
		return projectDao.selectList();
	}

	@GetMapping("/page/{page}/size/{size}")
	public ProjectDataVO list(@PathVariable int page, @PathVariable int size) {
		List<ProjectDto> list = projectDao.selectListByPaging(page, size);
		int count = projectDao.count();
		int endRow = page * size;
		boolean last = endRow >= count;
		return ProjectDataVO.builder().list(list)// 화면에 표시할 목록
				.count(count)// 총 데이터 개수
				.last(last)// 마지막 여부
				.build();
	}
	
	//사번말고 프로젝트 번호로 상세정보를 조회하는 매핑을 구현(selectOne)..
	@GetMapping("/getProjectName/{projectNo}")
	public String projectFind(@PathVariable int projectNo) {
		ProjectDto projectDto = projectDao.selectOne(projectNo);

		return projectDto.getProjectName();
	}///

	
	
	@GetMapping("/{empNo}")
	public List<ProjectDto> myList(@PathVariable int empNo) {
		return projectDao.myList(empNo);
	}

	
	//등록
		@Operation(
			description = "프로젝트 등록",
			responses = {
				@ApiResponse(responseCode = "200",description = "프로젝트 등록 완료",
					content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ProjectDto.class)
					)
				),
				@ApiResponse(responseCode = "500",description = "서버 오류",
					content = @Content(
							mediaType = "text/plain",
							schema = @Schema(implementation = String.class), 
							examples = @ExampleObject("server error")
					)
				),
			}
		)
	@PostMapping("/")
	public ResponseEntity<ProjectDto> insert(
			//@Parameter(description = "생성할 학생 정보에 대한 입력값", required = true, schema = @Schema(implementation = ProjectDto.class))
			@RequestBody ProjectDto projectDto) {
			
			 EmpDto empDto = empDao.selectOne(projectDto.getEmpNo());
			 int sequence = projectDao.sequence();
			 projectDto.setProjectNo(sequence);
			 projectDto.setProjectWriter(empDto.getEmpName());
			 projectDto.setCompanyNo(empDto.getCompanyNo());
			 
			 projectDao.insert(projectDto);
				
		return ResponseEntity.ok().body(projectDao.selectOne(sequence));
	}
	// 수정
		@Operation(
				description = "프로젝트 정보 변경",
				responses = {
					@ApiResponse(responseCode = "200",description = "변경 완료",
						content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ProjectDto.class)
						)
					),
					@ApiResponse(responseCode = "404",description = "프로젝트 정보 없음",
						content = @Content(
								mediaType = "text/plain",
								schema = @Schema(implementation = String.class), 
								examples = @ExampleObject("not found")
						)
					),
					@ApiResponse(responseCode = "500",description = "서버 오류",
						content = @Content(
								mediaType = "text/plain",
								schema = @Schema(implementation = String.class), 
								examples = @ExampleObject("server error")
						)
					),
				}
			)
		//수정
	@PatchMapping("/")
	public ResponseEntity<ProjectDto> edit(@RequestBody ProjectDto projectDto) {
		boolean result = projectDao.edit(projectDto);
		if (result == false) 
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(projectDao.selectOne(projectDto.getProjectNo()));
		}

		
		//사원리스트
		// 프로젝트 생성 시 회사의 사원 목록 조회
//	    @GetMapping("/companyEmployees/{empNo}")
//	    public ResponseEntity<List<EmpDto>> getCompanyEmployees(@PathVariable int empNo) {
//	    	 // empNo를 통해 해당 사원의 회사 번호를 조회합니다.
//	        EmpDto emp = empDao.selectOne(empNo);
//	        System.out.println(emp);
//	        int companyNo = emp.getCompanyNo();
//	        System.out.println(companyNo);
//	        // 회사 번호를 통해 해당 회사의 사원 목록을 조회합니다.
//	        List<EmpDto> employees = empDao.getEmployeesByCompanyNo(companyNo);
//	        
//	        
//	        System.out.println(employees);
//	        return ResponseEntity.ok().body(employees);
//	    }
	    
		@GetMapping("/companyEmployees/{empNo}")
		public ResponseEntity<List<EmpDto>> getCompanyEmployeesInfo(@RequestHeader("Authorization") String token) {
		    // 토큰을 검증하고 사용자 정보를 가져오는 로직을 구현합니다.
			LoginVO loginVO = jwtService.parse(token);
			int loginId = loginVO.getLoginId();
			System.out.println("1번 수행 로그인ID(사번) : "+loginId);
		    // 사원의 사번을 통해 회사 번호를 조회합니다.
		    EmpDto emp = empDao.selectOne(loginId);
		    int companyNo = emp.getCompanyNo();
		    System.out.println("2번 수행 회사 번호 조회 : "+companyNo);
		    // 회사 번호를 통해 해당 회사의 사원 목록을 조회합니다.
		    List<EmpDto> employees = empDao.getEmployeesByCompanyNo(companyNo);
		    System.out.println("사원목록 : "+employees);
		    
		    
		    return ResponseEntity.ok().body(employees);
		}



}

