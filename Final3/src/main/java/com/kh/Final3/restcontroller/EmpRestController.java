package com.kh.Final3.restcontroller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.Final3.dao.EmpDao;
import com.kh.Final3.dao.PaymentDao;
import com.kh.Final3.dto.ChatroomDto;
import com.kh.Final3.dto.EmpDto;
import com.kh.Final3.service.AttachService;
import com.kh.Final3.service.JwtService;
import com.kh.Final3.vo.InputVO;
import com.kh.Final3.vo.LoginVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@CrossOrigin
@RestController
@RequestMapping("/emp")
public class EmpRestController {

   @Autowired
   private EmpDao empDao;
   @Autowired
   private PaymentDao paymentDao;
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
               .isPaid(paymentDao.isPaid(findDto.getCompanyNo()))
               .accessToken(accessToken)
               .refreshToken(refreshToken)
            .build());//200
      }
      else {
         return ResponseEntity.status(401).build();//401
      }
   }

   @GetMapping("/list/{empNo}")
   public ResponseEntity <List<EmpDto>> list(@RequestHeader("Authorization") String token){
		LoginVO loginVO = jwtService.parse(token);
		int empNo = loginVO.getLoginId();
	   EmpDto empDto = empDao.selectOne(empNo);
	   int companyNo = empDto.getCompanyNo();
	   List<EmpDto> empList = empDao.selectListByCompany(companyNo);
	   return ResponseEntity.ok().body(empList);
   }

	//상세
	@Operation(
		description = "사원 상세 정보 조회",
		responses = {
			@ApiResponse(responseCode = "200",description = "조회 완료",
					content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EmpDto.class)
					)
			),
			@ApiResponse(responseCode = "404",description = "사원 정보 없음",
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

   @GetMapping("/{empNo}")
   public ResponseEntity<EmpDto> find(@RequestHeader("Authorization") String token){
		LoginVO loginVO = jwtService.parse(token);
		int empNo = loginVO.getLoginId();
		EmpDto empDto = empDao.selectOne(empNo);
		if(empDto == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(empDto);
   }
   
	@Autowired
    private AttachService attachService;

     //첨부파일수정
     @PostMapping("/upload/{empNo}")
     public ResponseEntity<?> insertEdit(@PathVariable int empNo, @RequestParam("attach") MultipartFile attach) 
             throws IllegalStateException, IOException {
         if (!attach.isEmpty()) {
             boolean isFirst = false;
             try {
                 int attachNo = empDao.findAttach(empNo);
                 attachService.remove(attachNo);
                 int editAttachNo = attachService.save(attach);
                 empDao.connect(empNo, editAttachNo);
             } catch (Exception e) {
                 isFirst = true;
             } 
             if (isFirst) {
                 int inputAttachNo = attachService.save(attach);
                 empDao.connect(empNo, inputAttachNo);
             }
             return ResponseEntity.ok().build();
         }
         return ResponseEntity.status(404).build();
     }

   //수정
     @Operation(
         description = "사원 정보 변경",
         responses = {
             @ApiResponse(responseCode = "200",description = "변경 완료",
                 content = @Content(
                     mediaType = "application/json",
                     schema = @Schema(implementation = EmpDto.class)
                 )
             ),
             @ApiResponse(responseCode = "404",description = "사원 정보 없음",
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

     @PatchMapping("/edit")
     public ResponseEntity<EmpDto> editUnit(@RequestBody EmpDto empDto) {
         boolean result = empDao.editUnit(empDto);
         if(result == false) return ResponseEntity.notFound().build();
         return ResponseEntity.ok().body(empDao.selectOne(empDto.getEmpNo()));//수정 완료된 결과를 조회하여 반환
     }


}