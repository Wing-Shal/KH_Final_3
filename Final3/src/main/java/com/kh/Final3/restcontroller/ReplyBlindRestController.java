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

import com.kh.Final3.dao.ReplyBlindDao;
import com.kh.Final3.dto.BoardBlindDto;
import com.kh.Final3.dto.ReplyBlindDto;
import com.kh.Final3.service.JwtService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("/replyBlind")
public class ReplyBlindRestController {

	@Autowired
	private ReplyBlindDao replyBlindDao;
	
	@Autowired
	private JwtService jwtService;

//	댓글 목록
	@GetMapping("/")
	public List<ReplyBlindDto> list(){
		return replyBlindDao.selectList();
	}
	
	//등록
//		@PostMapping("/") 
//		public ReplyBlindDto insert(@RequestBody ReplyBlindDto replyBlindDto, @RequestHeader("Authorization") String token){ 
//			int sequence = replyBlindDao.sequence(); //번호생성
////			String companyName = empDao.selectOnebyCompanyNo(token.getparsedCompanyNo())
////			토큰 파싱해서 작성자 이름 가져오기
//			String companyName = jwtService.parse(token.substring(7)).getAdminId();
//			
////			댓글이 달린 게시글 번호 가져오기ㄴ
//			
//			replyBlindDto.setReplyBlindNo(sequence); //시퀀스
////			boardBlindDto.setBlindCom
//			replyBlindDao.insert(replyBlindDto); //등록
//			
//			return replyBlindDao.selectOne(sequence);
//		}
		
//		삭제
		//삭제
		@DeleteMapping("/{blindNo}")
		public ResponseEntity<?> delete(@PathVariable int replyBlindNo){
			boolean result = replyBlindDao.delete(replyBlindNo);
			if(result == false) return ResponseEntity.notFound().build();
			return ResponseEntity.ok().build();
		}
		
		//수정
		@PutMapping("/")
		public ResponseEntity<?> editAll(@RequestBody ReplyBlindDto replyBlindDto) {
			boolean result = replyBlindDao.editAll(replyBlindDto);
			if(result == false) {
				//return ResponseEntity.notFound().build();
				return ResponseEntity.status(404).build();
			}
			return ResponseEntity.ok().build();
		}

}

