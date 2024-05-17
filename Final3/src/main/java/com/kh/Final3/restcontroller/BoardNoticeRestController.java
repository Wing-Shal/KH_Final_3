package com.kh.Final3.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.Final3.dao.BoardNoticeDao;
import com.kh.Final3.dao.EmpDao;
import com.kh.Final3.dto.BoardNoticeDto;
import com.kh.Final3.dto.EmpDto;
import com.kh.Final3.service.JwtService;
import com.kh.Final3.vo.LoginVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@CrossOrigin
@RestController
@RequestMapping("/boardNotice")
public class BoardNoticeRestController {
	
	@Autowired
	private BoardNoticeDao boardNoticeDao;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private EmpDao empDao;	
	
	//회사 공지 목록
	@Operation(
		description = "회사 공지 목록 조회",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "조회 성공",
				content = {
					@Content(
						mediaType = "application/json",
						array = @ArraySchema(
							schema = @Schema(implementation = BoardNoticeDto.class)
						)
					) 
				}
			),
			@ApiResponse(
				responseCode = "500",
				description = "서버 오류",
				content = {
					@Content(
						mediaType = "text/plain",
						schema = @Schema(implementation = String.class),
						examples = @ExampleObject("server error")
					)
				}
			)
		}
	)
	@GetMapping("/")
	public ResponseEntity<List<BoardNoticeDto>> list(@RequestHeader("Authorization") String token) {
	    LoginVO loginVO = jwtService.parse(token);

	    if (loginVO == null || loginVO.getLoginLevel() == null || loginVO.getLoginId() == null) {
	        return ResponseEntity.status(403).build();
	    }

	    int companyNo;
	    if ("회사".equals(loginVO.getLoginLevel())) {
	        companyNo = loginVO.getLoginId();
	    } 
	    else {
	        Integer empNo = loginVO.getLoginId();
	        EmpDto empDto = empDao.selectOne(empNo);
	        if (empDto == null) {
	        	return ResponseEntity.status(403).build();
	        }
	        companyNo = empDto.getCompanyNo();
	    }

	    List<BoardNoticeDto> notices = boardNoticeDao.selectList(companyNo);
	    return ResponseEntity.ok(notices);
	}

	
	//회사 공지 상세 
	@Operation(
			description = "회사 공지 상세 조회",
			responses = {
				@ApiResponse(
					responseCode = "200",
					description = "조회 성공",
					content = {
						@Content(
							mediaType = "application/json",
							schema = @Schema(implementation = BoardNoticeDto.class)
						)
					}
				),
				@ApiResponse(
					responseCode = "404",
					description = "해당 공지 번호 없음",
					content = @Content(
						mediaType = "text/plain", 
						schema = @Schema(implementation = String.class),
						examples = @ExampleObject("not found")
					)
				),
				@ApiResponse(
					responseCode = "500",
					description = "서버 오류",
					content = @Content(
						mediaType = "text/plain", 
						schema = @Schema(implementation = String.class),
						examples = @ExampleObject("server error")
					)
				)
			}
		)
	@GetMapping("/{noticeNo}")
	public ResponseEntity<BoardNoticeDto> find(@RequestHeader("Authorization") String token, @PathVariable int noticeNo) {
		LoginVO loginVO = jwtService.parse(token);
		BoardNoticeDto boardNoticeDto = boardNoticeDao.selectOne(noticeNo);
		
		if(loginVO.getLoginLevel().equals("회사")) {
			int companyNo = loginVO.getLoginId();
			if(boardNoticeDto == null) {
				return ResponseEntity.status(404).build();
			}
			else if(boardNoticeDto.getCompanyNo() != companyNo) {
				return ResponseEntity.status(403).build();
			}
			return ResponseEntity.status(200).body(boardNoticeDto);
		}
		else {
			Integer empNo = loginVO.getLoginId();
			EmpDto empDto = empDao.selectOne(empNo);
			if(boardNoticeDto == null) {
				return ResponseEntity.status(404).build();
			}
			else if(boardNoticeDto.getCompanyNo() != empDto.getCompanyNo()) {
				return ResponseEntity.status(403).build();
			}
			return ResponseEntity.status(200).body(boardNoticeDto);
		}
	}
	
	//등록
	@Operation(
			description = "회사 공지 등록",
			responses = {
				@ApiResponse(responseCode = "200",description = "회사 공지 등록 완료",
					content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = BoardNoticeDto.class)
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
	//등록은 무조건 회사아이디로
	public ResponseEntity<?> insert(@RequestHeader("Authorization") String token, @RequestBody BoardNoticeDto boardNoticeDto) { 
		int sequence = boardNoticeDao.sequence(); //번호생성
		boardNoticeDto.setNoticeNo(sequence); //번호설정
		
		LoginVO loginVO = jwtService.parse(token);
		boolean isCompanyId = loginVO.getLoginLevel().equals("회사");
	    if (!isCompanyId) {
	        return ResponseEntity.status(403).body("회사 아이디가 아닙니다.");
	    }
		int companyNo = loginVO.getLoginId();
		
		boardNoticeDto.setCompanyNo(companyNo);
		boardNoticeDao.insert(boardNoticeDto); //등록
		
		
		return ResponseEntity.ok(boardNoticeDao.selectOne(sequence));
	}
	
	//수정
	@PatchMapping("/")
	public ResponseEntity<?> editUnit(@RequestHeader("Authorization") String token, @RequestBody BoardNoticeDto boardNoticeDto) {
		LoginVO loginVO = jwtService.parse(token);
		boolean isCompanyId = loginVO.getLoginLevel().equals("회사");
		boolean isSameCompany = loginVO.getLoginId() == boardNoticeDto.getCompanyNo();
		
	    if (!isCompanyId && !isSameCompany) {
	        return ResponseEntity.status(403).body("수정권한 없음.");
	    }
	    int companyNo = loginVO.getLoginId();
		boardNoticeDto.setCompanyNo(companyNo);
		
	    
		boolean result = boardNoticeDao.editUnit(boardNoticeDto);
//		System.out.println(boardNoticeDto);
//		System.out.println(result);
		if(result == false) {
			//return ResponseEntity.notFound().build();
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}
	
	//삭제
//	@Operation(
//		description = "회사 공지 삭제",
//		responses = {
//			@ApiResponse(responseCode = "200",description = "삭제 완료",
//				content = @Content(
//						mediaType = "text/plain",
//						schema = @Schema(implementation = String.class),
//						examples = @ExampleObject("ok")
//				)
//			),
//			@ApiResponse(responseCode = "404",description = "해당 공지 번호 없음",
//				content = @Content(
//						mediaType = "text/plain",
//						schema = @Schema(implementation = String.class), 
//						examples = @ExampleObject("not found")
//				)
//			),
//			@ApiResponse(responseCode = "500",description = "서버 오류",
//				content = @Content(
//						mediaType = "text/plain",
//						schema = @Schema(implementation = String.class), 
//						examples = @ExampleObject("server error")
//				)
//			),
//		}
//	)
//	
//	@DeleteMapping("/{noticeNo}")
//	public ResponseEntity<?> delete(@PathVariable int noticeNo) {
//		boolean result = boardNoticeDao.delete(noticeNo);
//		if(result == false) return ResponseEntity.notFound().build();
//		return ResponseEntity.ok().build();
//	}
	

}
