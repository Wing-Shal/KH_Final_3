package com.kh.Final3.restcontroller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.Final3.dao.EmpDao;
import com.kh.Final3.dto.EmpDto;
import com.kh.Final3.service.AttachService;
import com.kh.Final3.service.JwtService;
import com.kh.Final3.vo.InputVO;
import com.kh.Final3.vo.LoginVO;


@CrossOrigin
@RestController
@RequestMapping("/emp")
public class EmpRestController {
   
   @Autowired
   private EmpDao empDao;
   
   @Autowired
   private JwtService jwtService;
   
   @PostMapping("/login")
   public ResponseEntity<LoginVO> login(@RequestBody InputVO inputVO) {
      EmpDto findDto = empDao.selectOne(Integer.parseInt(inputVO.getId()));
      if(findDto == null) {
         return ResponseEntity.status(404).build();//404
      }
      
      boolean isValid = findDto.getEmpPw().equals(inputVO.getPw());

      if(isValid) {
         String accessToken = jwtService.createAccessToken(findDto);
         String refreshToken = jwtService.createRefreshToken(findDto);
         
         return ResponseEntity.ok().body(LoginVO.builder()
               .loginId(findDto.getEmpNo())//사원아이디
               .loginLevel(findDto.getEmpType())//사원등급(사원,임원)
               .accessToken(accessToken)
               .refreshToken(refreshToken)
            .build());//200
      }
      else {
         return ResponseEntity.status(401).build();//401
      }
   }
   
   @Autowired
   private AttachService attachService;
   
	@PostMapping("/upload")
	public Integer upload(@RequestParam MultipartFile empAttach) throws IllegalStateException, IOException {
		
//	   if(empAttach.isEmpty()) {
//		   int attachNo = attachService.save(empAttach);
//	   }
	   
		if(empAttach.isEmpty()) return null;
	    //비어있으면 null 리턴
	    return attachService.save(empAttach);
		//사진 하나만 올리고 저장!
	    
	}
	
   
}