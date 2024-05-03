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
		public ResponseEntity<BoardBlindDto> insert(
				//@Parameter(description = "생성할 학생 정보에 대한 입력값", required = true, schema = @Schema(implementation = ProjectDto.class))
				@RequestBody BoardBlindDto boardBlindDto) {
				
				 EmpDto empDto = empDao.selectOne(boardBlindDto.getBlindEmpNo());
				// CompanyDto companyDto = companyDao.selectOne(boardBlindDto.getCompanyName());
				 int sequence = boardBlindDao.sequence();
				
				 boardBlindDto.setBlindNo(sequence);
				 //boardBlindDto.setEmpNo(empDto.getEmpNo());
				 
				// boardBlindDto.setBlindWriterCompany(companyDto.getCompanyName());
				 
				 boardBlindDao.insert(boardBlindDto);
				 
				 System.out.println("보드정보"+boardBlindDto);
				 System.out.println("사원정보"+empDto);
			return ResponseEntity.ok().body(boardBlindDao.selectOne(sequence));
		}
			
			//조회
			
			@GetMapping("/")
			public List<BoardBlindDto> list() {
				return boardBlindDao.selectList();
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
		@DeleteMapping("/{projectNo}")
		public ResponseEntity<?> delete(@PathVariable int blindNo) {
			boolean result = boardBlindDao.delete(blindNo);
						if (result == false) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok().build();
		}
}