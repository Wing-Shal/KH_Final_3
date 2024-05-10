package com.kh.Final3.restcontroller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.Final3.dao.AdminDao;
import com.kh.Final3.dao.AttachDao;
import com.kh.Final3.dto.AdminAttachDto;
import com.kh.Final3.dto.AdminDto;
import com.kh.Final3.service.AttachService;
import com.kh.Final3.service.JwtService;
import com.kh.Final3.vo.InputVO;
import com.kh.Final3.vo.LoginVO;


@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminRestController {
   
   @Autowired
   private AdminDao adminDao;
   
   @Autowired
   private JwtService jwtService;
   
   @PostMapping("/login")
   public ResponseEntity<LoginVO> login(@RequestBody InputVO inputVO) {
      AdminDto findDto = adminDao.selectOne(Integer.parseInt(inputVO.getId()));
      if(findDto == null) {
         return ResponseEntity.status(404).build();
      }
      
      boolean isValid = findDto.getAdminPw().equals(inputVO.getPw());

      if(isValid) {
         String accessToken = jwtService.createAccessToken(findDto);
         String refreshToken = jwtService.createRefreshToken(findDto);
         
         return ResponseEntity.ok().body(LoginVO.builder()
               .loginId(findDto.getAdminId())
               .loginLevel("운영자")
               .isPaid("운영자")
               .accessToken(accessToken)
               .refreshToken(refreshToken)
            .build());//200
      }
      else {
         return ResponseEntity.status(401).build();
      }
   }
   
   @Autowired
   private AttachService attachService;
   
   //첨부파일수정
   @PostMapping("/upload")
   public ResponseEntity<?> insertEdit(@RequestBody MultipartFile attach) 
           throws IllegalStateException, IOException {
	   //DB에 업로드
       int attachNo = attachService.save(attach);
       //시퀀스 번호 발급
       int adminAttachNo = adminDao.sequence();
       //연결
       AdminAttachDto adminAttachDto = new AdminAttachDto();
       adminAttachDto.setAdminAttachNo(adminAttachNo);
       adminAttachDto.setAttachNo(attachNo);
       adminDao.connectAttach(adminAttachDto);
       
       return ResponseEntity.ok().build();
   }
}