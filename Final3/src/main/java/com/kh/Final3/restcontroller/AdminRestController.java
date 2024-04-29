package com.kh.Final3.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.Final3.dao.AdminDao;
import com.kh.Final3.dto.AdminDto;
import com.kh.Final3.service.JwtService;
import com.kh.Final3.vo.AdminLoginVO;


@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminRestController {
   
   @Autowired
   private AdminDao adminDao;
   
   @Autowired
   private JwtService jwtService;
   
   @PostMapping("/login")
   public ResponseEntity<AdminLoginVO> login(@RequestBody AdminDto adminDto) {
      AdminDto findDto = adminDao.selectOne(adminDto.getAdminId());
      if(findDto == null) {
         return ResponseEntity.status(404).build();
      }
      
      boolean isValid = findDto.getAdminPw().equals(adminDto.getAdminPw());

      if(isValid) {
         String accessToken = jwtService.createAccessToken(findDto);
         String refreshToken = jwtService.createRefreshToken(findDto);
         
         return ResponseEntity.ok().body(AdminLoginVO.builder()
               .adminId(findDto.getAdminId())
               .accessToken(accessToken)
               .refreshToken(refreshToken)
            .build());//200
      }
      else {
         return ResponseEntity.status(401).build();
      }
   }
   //@PostMapping("/refresh")
   public ResponseEntity<AdminLoginVO> refresh(@RequestHeader("Authorization") String refreshToken) {
      try {
         AdminLoginVO loginVO = jwtService.parse(refreshToken);
         AdminDto adminDto = adminDao.selectOne(loginVO.getAdminId());
         if (adminDto == null) {
            throw new Exception("존재하지 않는 아이디");
         }
         String accessToken = jwtService.createAccessToken(adminDto);
         String newRefreshToken = jwtService.createRefreshToken(adminDto);
         
         return ResponseEntity.ok().body(AdminLoginVO.builder()
               .adminId(adminDto.getAdminId())
               .accessToken(accessToken)
               .refreshToken(newRefreshToken)
            .build());
         
      } catch(Exception e) {
         return ResponseEntity.status(401).build();
      }
   }
}