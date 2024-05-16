
package com.kh.Final3.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.kh.Final3.vo.DocumentDataVO;
import com.kh.Final3.vo.LoginVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@CrossOrigin
@RestController
@RequestMapping("/document")
public class DocumentRestController {

	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private DocumentDao documentDao;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private EmpDao empDao;
	@Autowired
	private ProjectDao projectDao;

	// 문서용 설정 추가
	@Operation(description = "문서 목록 조회", responses = {
			@ApiResponse(responseCode = "200", description = "조회 성공", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DocumentDto.class))) }),
			@ApiResponse(responseCode = "500", description = "서버 오류", content = {
					@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class), examples = @ExampleObject("server error")) }) })

	// 조회 (무한스크롤)
	@GetMapping("/")
	public List<DocumentDto> list() {
		return documentDao.selectList();
	}

	// 페이지별 문서 목록 조회
	@GetMapping("/page/{page}/size/{size}")
	public DocumentDataVO list(@PathVariable int page, @PathVariable int size) {
		List<DocumentDto> list = documentDao.selectListByPaging(page, size);
		int count = documentDao.count();
		int endRow = page * size;
		boolean last = endRow >= count;
		return DocumentDataVO.builder().list(list)// 화면에 표시할 목록
				.count(count)// 총 데이터 개수
				.last(last)// 마지막 여부
				.build();
	}

	// 문서 상세 조회 조회되지 않는 경우 404번 처리
	@Operation(description = "문서 상세 조회", responses = {
			@ApiResponse(responseCode = "200", description = "조회 성공", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = DocumentDto.class)) }),
			@ApiResponse(responseCode = "404", description = "해당 문서의 데이터가 없음", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class), examples = @ExampleObject("not found"))),
			@ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class), examples = @ExampleObject("server error"))) })

//	@GetMapping("/{documentNo}")
//	public ResponseEntity<DocumentDto> find(@PathVariable int documentNo) {
//		DocumentDto documentDto = documentDao.selectOne(documentNo);
//		if (documentDto == null) {
//			// return ResponseEntity.notFound().build();
//			return ResponseEntity.status(404).build();
//		}//문서가 없는경우 404반환
//		// return ResponseEntity.ok(empDto);
//		return ResponseEntity.status(200).body(documentDto);
//		//문서를 찾은 경우 해당문서 반환 
//	}///

	// 문서 등록
	@PostMapping("/")
	public ResponseEntity<DocumentDto> insert(
			// @Parameter(description = "생성할 학생 정보에 대한 입력값", required = true, schema =
			// @Schema(implementation = ProjectDto.class))
			@RequestBody DocumentDto documentDto) {

		EmpDto empDto = empDao.selectOne(documentDto.getEmpNo());

		ProjectDto projectDto = projectDao.selectOne(documentDto.getProjectNo());

		System.out.println("프로젝트" + projectDto);
		int sequence = documentDao.sequence();
		documentDto.setDocumentNo(sequence);
		// int sequence2 = projectDao.sequence();
		// projectDto.setProjectNo(sequence);

		documentDto.setProjectNo(projectDto.getProjectNo());

		// documentDto.setProjectName(projectDto.getProjectName());
		documentDto.setDocumentWriter(empDto.getEmpName());
//		documentDto.setDocumentApprover(empDto.getEmpName());

		documentDao.insert(documentDto);

		return ResponseEntity.ok().body(documentDao.selectOne(sequence));
	}

	// 프로젝트 번호로 해당 문서 조회
	@GetMapping("/{projectNo}")
	public List<DocumentDto> docuList(@PathVariable int projectNo) {
		return documentDao.docuList(projectNo);
	}

	// 수정
	@Operation(description = "문서 변경", responses = {
			@ApiResponse(responseCode = "200", description = "변경 완료", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectDto.class))),
			@ApiResponse(responseCode = "404", description = "문서 정보 없음", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class), examples = @ExampleObject("not found"))),
			@ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class), examples = @ExampleObject("server error"))), })
	// 수정
	@PutMapping("/")
	public ResponseEntity<DocumentDto> edit(@RequestBody DocumentDto documentDto) {
		boolean result = documentDao.edit(documentDto);
		if (result == false)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(documentDao.selectOne(documentDto.getDocumentNo()));
	}

	// 삭제
	@DeleteMapping("/{documentNo}")
	public ResponseEntity<?> delete(@PathVariable int documentNo) {
		boolean result = documentDao.delete(documentNo);
		if (result == false) {
			return ResponseEntity.notFound().build();
			// 문서가 없는 경우 404 에러 반환
		}
		return ResponseEntity.ok().build();
	}
	
	//결재자 사원목록
	@GetMapping("/companyEmployees/{empNo}")
	public ResponseEntity<List<EmpDto>> getCompanyEmployeesInfo(@RequestHeader("Authorization") String token) {
	    // 토큰을 검증하고 사용자 정보를 가져오는 로직을 구현합니다.
		LoginVO loginVO = jwtService.parse(token);
		int loginId = loginVO.getLoginId();
		System.out.println("문서 1번 수행 로그인ID(사번) : "+loginId);
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