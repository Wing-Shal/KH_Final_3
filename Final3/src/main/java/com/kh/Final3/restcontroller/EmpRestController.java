package com.kh.Final3.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.Final3.dao.EmpDao;
import com.kh.Final3.dto.EmpDto;
import com.kh.Final3.service.JwtService;
import com.kh.Final3.vo.EmpLoginVO;


@CrossOrigin
@RestController
@RequestMapping("/emp")
public class EmpRestController {
   
   @Autowired
   private EmpDao empDao;
   
   @Autowired
   private JwtService jwtService;
   
   @PostMapping("/login")
   public ResponseEntity<EmpLoginVO> login(@RequestBody EmpDto empDto) {
      EmpDto findDto = empDao.selectOne(empDto.getEmpNo());
      if(findDto == null) {
         return ResponseEntity.status(404).build();//404
      }
      
      boolean isValid = findDto.getEmpPw().equals(empDto.getEmpPw());

      if(isValid) {
         String accessToken = jwtService.createAccessToken(findDto);
         String refreshToken = jwtService.createRefreshToken(findDto);
         
         return ResponseEntity.ok().body(EmpLoginVO.builder()
               .empNo(findDto.getEmpNo())//사원아이디
               .empType(findDto.getEmpType())//사원등급(사원,임원)
               .accessToken(accessToken)
               .refreshToken(refreshToken)
            .build());//200
      }
      else {
         return ResponseEntity.status(401).build();//401
      }
   }
   //refresh token으로 로그인하는 매핑
   //- header에 있는 Authorization이라는 항목을 읽어 해석한 뒤 결과를 반환
   //- 토큰이 만료되었다면(잘못된토큰/시간지남/...) 401 반환
   @PostMapping("/refresh")
   public ResponseEntity<EmpLoginVO> refresh(
         @RequestHeader("Authorization") String refreshToken) {
      try {
         EmpLoginVO loginVO = jwtService.parseEmp(refreshToken);
         EmpDto empDto = empDao.selectOne(loginVO.getEmpNo());
         if (empDto == null) {
            throw new Exception("존재하지 않는 아이디");
         }
         if(!loginVO.getEmpType().equals(empDto.getEmpType())) {
            throw new Exception("정보 불일치");
         }
         //위에서 필터링되지 않았다면 refresh token이 유효하다고 볼 수 있다
         //-> 사용자에게 새롭게 access token을 발급한다
         //-> 보안을 위해서 refresh token도 재발급한다
         
         String accessToken = jwtService.createAccessToken(empDto);
         String newRefreshToken = jwtService.createRefreshToken(empDto);
         
         return ResponseEntity.ok().body(EmpLoginVO.builder()
               .empNo(empDto.getEmpNo())
               .empType(empDto.getEmpType())
               .accessToken(accessToken)
               .refreshToken(newRefreshToken)
            .build());
         
      } catch(Exception e) {
         return ResponseEntity.status(401).build();
      }
   }
}