package com.kh.Final3.vo;

import java.util.List;

import com.kh.Final3.dto.BoardBlindDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//무한스크롤
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class BoardBlindDataVO {
	private List<BoardBlindDto> list;
	private int count;
	private boolean last;
}
