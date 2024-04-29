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

import com.kh.Final3.dao.ReplyDocumentDao;
import com.kh.Final3.dto.BoardNoticeDto;
import com.kh.Final3.dto.ReplyDocumentDto;
import com.kh.Final3.service.JwtService;

@CrossOrigin
@RestController
@RequestMapping("/replyDocument")
public class ReplyDocumentRestController {

	@Autowired
	private ReplyDocumentDao replyDocumentDao;
	
	@Autowired
	private JwtService jwtService;
	
	//댓글 목록
	@GetMapping("/")
	public List<ReplyDocumentDto> list(){
		return replyDocumentDao.selectList();
	}
	
	//댓글 등록
	@PostMapping("/") 
	public ReplyDocumentDto insert(@RequestBody ReplyDocumentDto replyDocumentDto){ 
		int sequence = replyDocumentDao.sequence(); //번호생성		
		replyDocumentDto.setReplyDocumentNo(sequence); //번호설정
		replyDocumentDao.insert(replyDocumentDto); //등록
		
		return replyDocumentDao.selectOne(sequence);
	}
	
	//삭제
			@DeleteMapping("/{replyDocumentNo}")
			public ResponseEntity<?> delete(@PathVariable int replyDocumentNo){
				boolean result = replyDocumentDao.delete(replyDocumentNo);
				if(result == false) return ResponseEntity.notFound().build();
				return ResponseEntity.ok().build();
			}
	
}
