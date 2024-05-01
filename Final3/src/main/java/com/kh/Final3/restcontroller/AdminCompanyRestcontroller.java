package com.kh.Final3.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.Final3.dao.CompanyDao;
import com.kh.Final3.dto.CompanyDto;
import com.kh.Final3.vo.CompanySearchVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/admin/company")
public class AdminCompanyRestcontroller {
	
	@Autowired
	private CompanyDao companyDao;
	
//	@GetMapping("/")//전체조회
//	public List<CompanyDto> list() {
//		return companyDao.selectList();
//	}
	//빈문자열을 null로 처리하는 도구 설정
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
//	}
//	@GetMapping("/")//옵션+전체 조회
//	public List<CompanyDto> search(@RequestBody CompanySearchVO companySearchVO) {
//		log.debug(companySearchVO.toString());
//		return companyDao.selectList(companySearchVO);
//	}
	@GetMapping("/")
	public List<CompanyDto> search() {
		return companyDao.list();
	}
	@PutMapping("/")//전체수정
	public boolean editAll(@RequestBody CompanyDto companyDto) {
		return companyDao.editAll(companyDto);
	}
	@PatchMapping("/")//일부수정
	public boolean editUnit(@RequestBody CompanyDto companyDto) {
		System.out.println(companyDto);
		return companyDao.editUnit(companyDto);
	}
	
	
}
