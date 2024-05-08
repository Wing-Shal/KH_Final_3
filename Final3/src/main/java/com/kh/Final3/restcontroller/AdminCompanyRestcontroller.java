package com.kh.Final3.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.Final3.dao.AdminCompanyDao;
import com.kh.Final3.dao.CompanyDao;
import com.kh.Final3.dao.PaymentDao;
import com.kh.Final3.dto.CompanyDto;
import com.kh.Final3.service.EmailService;
import com.kh.Final3.vo.AdminCompanyVO;
import com.kh.Final3.vo.CompanySearchVO;
import com.kh.Final3.vo.PaymentHistoryVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/admin/company")
public class AdminCompanyRestcontroller {
	
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private AdminCompanyDao adminCompanyDao;
	@Autowired
	private PaymentDao paymentDao;
	@Autowired
	private EmailService emailService;

	@GetMapping("/")
	public List<AdminCompanyVO> search() {
		return adminCompanyDao.CompanyListWithStatus();
	}
	@GetMapping("/paymentHistory/{companyNo}")
	public List<PaymentHistoryVO> history(@PathVariable int companyNo) {
		return paymentDao.selectHistoryByCompanyNo(companyNo);
	}
	@PutMapping("/")//전체수정
	public boolean editAll(@RequestBody CompanyDto companyDto) {
		return companyDao.editAll(companyDto);
	}
	@PatchMapping("/")//일부수정
	public boolean editUnit(@RequestBody CompanyDto companyDto) {
		return companyDao.editUnit(companyDto);
	}
	@PatchMapping("/approve/{companyNo}")
	public boolean approveCompany(@PathVariable int companyNo) {
		//승인 이메일 발송
		CompanyDto companyDto = companyDao.selectOne(companyNo);
		emailService.sendApproveMail(companyDto);
		return companyDao.approveCompany(companyNo);
	}
}
