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

import com.kh.Final3.dao.BoardBlindDao;
import com.kh.Final3.dao.CompanyDao;
import com.kh.Final3.dao.EmpDao;
import com.kh.Final3.dao.ReplyBlindDao;
import com.kh.Final3.dto.BoardBlindDto;
import com.kh.Final3.dto.CompanyDto;
import com.kh.Final3.dto.EmpDto;
import com.kh.Final3.dto.ReplyBlindDto;
import com.kh.Final3.service.JwtService;
import com.kh.Final3.vo.LoginVO;

@CrossOrigin
@RestController
@RequestMapping("/replyBlind")
public class ReplyBlindRestController {

	@Autowired
	private ReplyBlindDao replyBlindDao;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private EmpDao empDao;
	
	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private BoardBlindDao boardBlindDao;
	
//	댓글 목록
	@GetMapping("/")
	public List<ReplyBlindDto> list(){
		return replyBlindDao.selectList();
	}
	
	//requestBody 어노테이션 springFramework 제대로 쓰는지 꼭꼭꼭 확인
	@PostMapping("/")
	public ResponseEntity<ReplyBlindDto> insert(@RequestBody ReplyBlindDto replyBlindDto, @RequestHeader("Authorization") String token) {
	    // 토큰을 해석하여 사용자 정보 가져오기
	    LoginVO loginVO = jwtService.parse(token);
	    int sequence = replyBlindDao.sequence();
	    replyBlindDto.setReplyBlindNo(sequence);
	    
	    int loginId = loginVO.getLoginId(); // 토큰에서 사용자의 사원 번호 가져오기
	    EmpDto empDto = empDao.selectOne(loginId); // 사용자의 사원 정보 조회
	    int companyNo = empDto.getCompanyNo(); // 사용자의 회사 번호 가져오기
	    System.out.println("회사번호"+companyNo);
	    CompanyDto companyDto = companyDao.selectOne(companyNo); // 회사 정보 조회
	    String companyName = companyDto.getCompanyName(); // 회사 이름 가져오기
	    System.out.println("회사이름"+companyName);
	    replyBlindDto.setReplyEmpNo(loginId); // 게시글 작성자의 사원 번호 설정
	    System.out.println("작성자의 사원번호 설정"+replyBlindDto.getReplyEmpNo());
	    replyBlindDto.setReplyBlindCompany(companyName); // 게시글 작성자의 회사 이름 설정
	    System.out.println("작성자의 회사이름 설정"+replyBlindDto.getReplyBlindCompany());
//	    여기까지 디버깅 완료
	    replyBlindDao.insert(replyBlindDto); // 게시글 등록
	    ReplyBlindDto insertedBoard = replyBlindDao.selectOne(replyBlindDto.getReplyBlindNo());
	    System.out.println("replyBlindDto"+insertedBoard);
	    return ResponseEntity.ok().body(insertedBoard);
	}
		
//		삭제
		//삭제
		@DeleteMapping("/{replyBlindNo}")
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

