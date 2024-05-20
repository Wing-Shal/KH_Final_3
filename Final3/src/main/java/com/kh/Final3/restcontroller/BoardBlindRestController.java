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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.Final3.dao.BoardBlindDao;
import com.kh.Final3.dao.CompanyDao;
import com.kh.Final3.dao.EmpDao;
import com.kh.Final3.dto.BoardBlindDto;
import com.kh.Final3.dto.BoardNoticeDto;
import com.kh.Final3.dto.CompanyDto;
import com.kh.Final3.dto.DocumentDto;
import com.kh.Final3.dto.EmpDto;
import com.kh.Final3.dto.ProjectDto;
import com.kh.Final3.service.JwtService;
import com.kh.Final3.vo.BoardBlindDataVO;
import com.kh.Final3.vo.LoginVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@CrossOrigin
@RestController
@RequestMapping("/boardBlind")
public class BoardBlindRestController {

	@Autowired
	private BoardBlindDao boardBlindDao;
	@Autowired
	private EmpDao empDao;
	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private JwtService jwtService;
	
	//등록
			@Operation(
				description = "게시물 등록",
				responses = {
					@ApiResponse(responseCode = "200",description = "게시물 등록 완료",
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
			public ResponseEntity<BoardBlindDto> insert(@RequestBody BoardBlindDto boardBlindDto, @RequestHeader("Authorization") String token) {
			    // 토큰을 해석하여 사용자 정보 가져오기
				
			    LoginVO loginVO = jwtService.parse(token);
			    int sequence = boardBlindDao.sequence();
			    boardBlindDto.setBlindNo(sequence);
			    int loginId = loginVO.getLoginId(); // 토큰에서 사용자의 사원 번호 가져오기
			    EmpDto empDto = empDao.selectOne(loginId); // 사용자의 사원 정보 조회
			    int companyNo = empDto.getCompanyNo(); // 사용자의 회사 번호 가져오기
			    System.out.println("회사번호"+companyNo);
			    CompanyDto companyDto = companyDao.selectOne(companyNo); // 회사 정보 조회
			    String companyName = companyDto.getCompanyName(); // 회사 이름 가져오기
			    System.out.println("회사이름"+companyName);
			    boardBlindDto.setBlindEmpNo(loginId); // 게시글 작성자의 사원 번호 설정
			    boardBlindDto.setBlindPassword(Integer.toString(loginId));
			    System.out.println("작성자의 사원번호 설정"+boardBlindDto.getBlindEmpNo());
			    boardBlindDto.setBlindWriterCompany(companyName); // 게시글 작성자의 회사 이름 설정
			    System.out.println("작성자의 회사이름 설정"+boardBlindDto.getBlindWriterCompany());
//			    여기까지 디버깅 완료
			    
			    System.out.println(boardBlindDto);
			    // 여기에 게시글 작성 시간과 조회수, 비밀번호 등 필요한 정보를 설정할 수 있습니다.
			    boardBlindDao.insert(boardBlindDto); // 게시글 등록
			    BoardBlindDto insertedBoard = boardBlindDao.selectOne(boardBlindDto.getBlindNo());
			    return ResponseEntity.ok().body(insertedBoard);
			}

			
//			//조회
			@GetMapping("/")
			public List<BoardBlindDto> selectBlindList() {
				return boardBlindDao.selectBlindList();
			}
			
			//무한스크롤
			@GetMapping("/page/{page}/size/{size}")
			public BoardBlindDataVO list(@PathVariable int page, @PathVariable int size) {
				List<BoardBlindDto> list = boardBlindDao.selectListByPaging(page, size);
				int count = boardBlindDao.count();
				int endRow = page * size;
				boolean last = endRow >= count;
				return BoardBlindDataVO.builder()
							.list(list)//화면에 표시할 목록
							.count(count)//총 데이터 개수
							.last(last)//마지막 여부
						.build();
			}
			
			
			
//			상세조회
			@GetMapping("/{blindNo}")
			public List<BoardBlindDto> find(@PathVariable int blindNo){
				return boardBlindDao.find(blindNo);
			}
			
		// 수정
			@Operation(
					description = "게시물 정보 변경",
					responses = {
						@ApiResponse(responseCode = "200",description = "변경 완료",
							content = @Content(
								mediaType = "application/json",
								schema = @Schema(implementation = BoardBlindDto.class)
							)
						),
						@ApiResponse(responseCode = "404",description = "게시물 정보 없음",
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
		public ResponseEntity<BoardBlindDto> edit(@RequestBody BoardBlindDto boardBlindDto) {
			boolean result = boardBlindDao.edit(boardBlindDto);
			if (result == false) 
				return ResponseEntity.notFound().build();
			return ResponseEntity.ok().body(boardBlindDao.selectOne(boardBlindDto.getBlindNo()));
			}
		//삭제
			@Operation(
				description = "학생 정보 삭제",
				responses = {
					@ApiResponse(responseCode = "200",description = "삭제 완료",
						content = @Content(
								mediaType = "text/plain",
								schema = @Schema(implementation = String.class),
								examples = @ExampleObject("ok")
						)
					),
					@ApiResponse(responseCode = "404",description = "학생 정보 없음",
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
			
		// 삭제
		@DeleteMapping("/{blindNo}")
		public ResponseEntity<?> delete(@PathVariable int blindNo) {
			boolean result = boardBlindDao.delete(blindNo);
						if (result == false) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok().build();
		}
}