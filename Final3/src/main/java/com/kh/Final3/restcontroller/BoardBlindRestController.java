package com.kh.Final3.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.Final3.dao.BoardBlindDao;
import com.kh.Final3.dto.BoardBlindDto;
import com.kh.Final3.service.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@CrossOrigin
@RestController
@RequestMapping("/boardBlind")
public class BoardBlindRestController {

	@Autowired
	private BoardBlindDao boardBlindDao;

	@Autowired
	private JwtService jwtService;
	
	//블라인드 게시판 목록
		@Operation(
			description = "블라인드 리스트 조회",
			responses = {
				@ApiResponse(
					responseCode = "200",
					description = "조회 성공",
					content = {
						@Content(
							mediaType = "application/json",
							array = @ArraySchema(
								schema = @Schema(implementation = BoardBlindDto.class)
							)
						) 
					}
				),
				@ApiResponse(
					responseCode = "500",
					description = "오류",
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
	public List<BoardBlindDto> list(){
		return boardBlindDao.selectList();
	}
	
	//블라인드 게시판 상세
		@Operation(
				description = "블라인드 게시판 상세",
				responses = {
					@ApiResponse(
						responseCode = "200",
						description = "조회 성공",
						content = {
							@Content(
								mediaType = "application/json",
								schema = @Schema(implementation = BoardBlindDto.class)
							)
						}
					),
					@ApiResponse(
						responseCode = "404",
						description = "없음",
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
		
	@GetMapping("/{blindNo}")
	public ResponseEntity<BoardBlindDto> find(@PathVariable int blindNo){
		BoardBlindDto boardBlindDto = boardBlindDao.selectOne(blindNo);
		if(boardBlindDto == null) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.status(200).body(boardBlindDto);
	}

	//등록
	@PostMapping("/") 
	public BoardBlindDto insert(@RequestBody BoardBlindDto boardBlindDto, @RequestHeader("Authorization") String token){ 
		int sequence = boardBlindDao.sequence(); //번호생성
//		String companyName = empDao.selectOnebyCompanyNo(token.getparsedCompanyNo())
//		토큰 파싱해서 작성자 이름 가져오기
//		String companyName = jwtService.parse(token.substring(7)).getLoginId();
		
		boardBlindDto.setBlindNo(sequence); //시퀀스
//		boardBlindDto.setBlindCom
		boardBlindDao.insert(boardBlindDto); //등록
		
		return boardBlindDao.selectOne(sequence);
	}
	
	//삭제
	@DeleteMapping("/{blindNo}")
	public ResponseEntity<?> delete(@PathVariable int blindNo){
		boolean result = boardBlindDao.delete(blindNo);
		if(result == false) return ResponseEntity.notFound().build();
		return ResponseEntity.ok().build();
	}
	
	//수정
	@PutMapping("/")
	public ResponseEntity<?> editAll(@RequestBody BoardBlindDto boardBlindtDto){
		boolean result = boardBlindDao.editAll(boardBlindtDto);
		if(result == false) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}
	
}
