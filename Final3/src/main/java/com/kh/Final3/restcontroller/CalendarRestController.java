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

import com.kh.Final3.dao.CalendarDao;
import com.kh.Final3.dto.CalendarDto;
import com.kh.Final3.service.JwtService;
import com.kh.Final3.vo.LoginVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "일정 관리")

@CrossOrigin
@RestController
@RequestMapping("/calendar")
public class CalendarRestController {
	
	@Autowired
	private CalendarDao calendarDao;
	
	@Autowired
	private JwtService jwtService;
	
	//등록
	@Operation(
			description = "일정 등록",
			responses = {
				@ApiResponse(responseCode = "200",description = "일정 등록 완료",
					content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CalendarDto.class)
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
		public ResponseEntity<CalendarDto> insert(
				@Parameter(description = "등록할 일정에 대한 입력값", required = true, schema = @Schema(implementation = CalendarDto.class)) 
				@RequestBody CalendarDto calendarDto, @RequestHeader("Authorization") String token){
				
			int sequence = calendarDao.sequence();
			
	        LoginVO loginVO = jwtService.parse(token);
	        int empNo = loginVO.getLoginId();
	        
	        calendarDto.setCalendarWriter(empNo);
	        
			calendarDto.setCalendarNo(sequence);
			calendarDao.insert(calendarDto);
			return ResponseEntity.ok().body(calendarDao.selectOne(sequence));
		}
	
	//목록
	@Operation(
		description = "일정 목록 조회",
		responses = {
			@ApiResponse(responseCode = "200",description = "조회 완료",
				content = @Content(
						mediaType = "application/json",
						array = @ArraySchema(schema = @Schema(implementation = CalendarDto.class))
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
	
	@GetMapping("/")
	public ResponseEntity<List<CalendarDto>> list() {
		List<CalendarDto> list = calendarDao.selectList();
		return ResponseEntity.ok().body(list);
	}
	
	//상세
	@Operation(
		description = "일정 상세 정보 조회",
		responses = {
			@ApiResponse(responseCode = "200",description = "조회 완료",
					content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CalendarDto.class)
					)
			),
			@ApiResponse(responseCode = "404",description = "일정 정보 없음",
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
	@GetMapping("/{calendarNo}")
	public ResponseEntity<CalendarDto> find(@PathVariable int calendarNo){
		CalendarDto calendarDto = calendarDao.selectOne(calendarNo);
		if(calendarDto == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(calendarDto);
	}
	
	//전체수정
	@PutMapping("/")
	public ResponseEntity<?> editAll(@RequestBody CalendarDto calendarDto) {
		boolean result = calendarDao.editAll(calendarDto);
		if(result == false) {
			//return ResponseEntity.notFound().build();
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}
	
	//일부수정
	@PatchMapping("/")
	public ResponseEntity<?> editUnit(@RequestBody CalendarDto calendarDto) {
		boolean result = calendarDao.editUnit(calendarDto);
		if(result == false) {
			//return ResponseEntity.notFound().build();
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{calendarNo}")
	public ResponseEntity<?> delete(@PathVariable int calendarNo) {
		boolean result = calendarDao.delete(calendarNo);
		if(result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}
	
	
}
