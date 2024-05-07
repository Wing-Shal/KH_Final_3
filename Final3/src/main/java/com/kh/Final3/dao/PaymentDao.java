package com.kh.Final3.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.PaymentDto;

@Repository
public class PaymentDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int paymentSequence() {
		return sqlSession.selectOne("payment.paymentSequence");
	}
	public void insertPayment(PaymentDto paymentDto) {
		sqlSession.insert("payment.paymentAdd", paymentDto);
	}
	public void cancelSubscription(PaymentDto paymentDto) {
		sqlSession.insert("payment.paymentCancel", paymentDto);
	}
	public List<PaymentDto> selectListByCompanyNo(int companyNo) {
		return sqlSession.selectList("payment.paymentList", companyNo);
	}
}
