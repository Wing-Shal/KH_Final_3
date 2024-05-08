package com.kh.Final3.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.Final3.dto.PaymentDto;
import com.kh.Final3.vo.PaymentHistoryVO;

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
	public List<PaymentDto> selectActiveSubscription() {
		return sqlSession.selectList("payment.selectActiveSubscription");
	}
	public String isPaid(int companyNo) {
		String result = sqlSession.selectOne("payment.isPaid", companyNo);
		if(result == null) {
			return "INACTIVE";
		} else {
			return result;
		}
	}
	public List<PaymentHistoryVO> selectHistoryByCompanyNo(int companyNo) {
		return sqlSession.selectList("payment.selectHistory", companyNo);
	}
}
