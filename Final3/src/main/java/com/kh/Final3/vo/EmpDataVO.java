package com.kh.Final3.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//무한 스크롤을 위한 VO
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class EmpDataVO {
	private List<EmpInfoVO> list;
	private int count;
	private boolean last;
}
