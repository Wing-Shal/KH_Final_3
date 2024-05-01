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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.Final3.dao.DocumentDao;
import com.kh.Final3.dto.DocumentDto;
import com.kh.Final3.service.JwtService;
import com.kh.Final3.vo.DocumentDataVO;

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
	private DocumentDao documentDao;
	@Autowired
    private JwtService jwtService;
	
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

	//페이지별 문서 목록 조회
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
			@ApiResponse(responseCode = "404", description = "해당 문서의 데이터가 없음", content = 
			@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class), examples = 
			@ExampleObject("not found"))),
			@ApiResponse(responseCode = "500", description = "서버 오류", content = 
			@Content(mediaType = "text/plain", schema = 
			@Schema(implementation = String.class), examples = 
			@ExampleObject("server error"))) })

	@GetMapping("/{documentNo}")
	public ResponseEntity<DocumentDto> find(@PathVariable int documentNo) {
		DocumentDto documentDto = documentDao.selectOne(documentNo);
		if (documentDto == null) {
			// return ResponseEntity.notFound().build();
			return ResponseEntity.status(404).build();
		}//문서가 없는경우 404반환
		// return ResponseEntity.ok(empDto);
		return ResponseEntity.status(200).body(documentDto);
		//문서를 찾은 경우 해당문서 반환 
	}

	// 문서 등록
	@PostMapping("/")
	public DocumentDto save(@RequestBody DocumentDto documentDto) {//, @RequestHeader("Authorization") String token
		  // 토큰 파싱하여 작성자 이름 가져오기
		//String documentWriter = jwtService.parse(token).getAdminId();
        
		int sequence = documentDao.sequence();// 번호생성

		documentDto.setDocumentNo(sequence);// 번호설정
		//documentDto.setDocumentWriter(documentWriter); //문서작성자 설정
		
		documentDao.insert(documentDto);// 등록
		return documentDao.selectOne(sequence);// 등록된 결과를 조회하여 반환
	}

	// 전체 수정
	@PutMapping("/")
	public ResponseEntity<?> editAll(@RequestBody DocumentDto documentDto) {
		boolean result = documentDao.editAll(documentDto);
		if (result == false) {
			// return ResponseEntity.notFound().build();
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}

	// 삭제
	@DeleteMapping("/{documentNo}")
	public ResponseEntity<?> delete(@PathVariable int documentNo) {
		boolean result = documentDao.delete(documentNo);
		if (result == false) {
			return ResponseEntity.notFound().build();
			//문서가 없는 경우 404 에러 반환
		}
		return ResponseEntity.ok().build();
	}
}

