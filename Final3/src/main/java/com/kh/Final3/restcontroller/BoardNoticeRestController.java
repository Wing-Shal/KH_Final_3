package com.kh.Final3.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.Final3.dao.BoardNoticeDao;
import com.kh.Final3.dto.BoardNoticeDto;
import com.kh.Final3.service.JwtService;

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
	public List<BoardNoticeDto> list() {
		return boardNoticeDao.selectList();
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
	public ResponseEntity<BoardNoticeDto> find(@PathVariable int noticeNo) {
		BoardNoticeDto boardNoticeDto = boardNoticeDao.selectOne(noticeNo);
		if(boardNoticeDto == null) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.status(200).body(boardNoticeDto);
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
	public BoardNoticeDto insert(@RequestBody BoardNoticeDto boardNoticeDto){ 
		int sequence = boardNoticeDao.sequence(); //번호생성
//		int comapnyNo = companyDao.selectOneByEmpNo(jwtService.parse(token));
		
		
		boardNoticeDto.setNoticeNo(sequence); //번호설정
//		boardNoticeDto.setCompanyNo(comapnyNo);
		boardNoticeDao.insert(boardNoticeDto); //등록
		
		return boardNoticeDao.selectOne(sequence);
	}
	
	//삭제
	@Operation(
		description = "회사 공지 삭제",
		responses = {
			@ApiResponse(responseCode = "200",description = "삭제 완료",
				content = @Content(
						mediaType = "text/plain",
						schema = @Schema(implementation = String.class),
						examples = @ExampleObject("ok")
				)
			),
			@ApiResponse(responseCode = "404",description = "해당 공지 번호 없음",
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
	
	@DeleteMapping("/{noticeNo}")
	public ResponseEntity<?> delete(@PathVariable int noticeNo) {
		boolean result = boardNoticeDao.delete(noticeNo);
		if(result == false) return ResponseEntity.notFound().build();
		return ResponseEntity.ok().build();
	}
	

}
