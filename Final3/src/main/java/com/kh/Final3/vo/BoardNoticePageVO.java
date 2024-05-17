package com.kh.Final3.vo;

import java.util.List;

import com.kh.Final3.dto.BoardNoticeDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@Builder@NoArgsConstructor@AllArgsConstructor
public class BoardNoticePageVO {
	private String column, keyword; //검색항목, 검색어
	private List<BoardNoticeDto> list;
	private int page; //현재 페이지
	private int size; //한페이지에 보여줄 게시글 개수
	private int count; //전체 개수
	private int blockSize; //블럭 표시 개수
	
	
	public String getColumn() {
		if(column == null) return "";
		return column;
	}

	public String getKeyword() {
		if(keyword == null) return "";
		return keyword;
	}

	
	//계산을 위한 가상의 Getter 메소드 추가
	public boolean isSearch() {
		return column != null && keyword != null;
	}
	
	public int getBeginRow() {
		//return getEndRow() - (page - 1);
		return page * size - (size - 1);
	}
	
	public int getEndRow() {
		return page * size;
	}
	
	public int getTotalPage() {
		return (count - 1) / size + 1;
	}
	
	public int getBeginBlock() {
		return (page - 1) / blockSize * blockSize + 1;
	}
	
	public int getEndBlock() {
		int number = (page - 1) / blockSize * blockSize + blockSize;
		return Math.min(getTotalPage(), number);
	}
	
	// 논리 반환값을 가지는 Getter 메소드는 get이 아니라 is로 시작한다
	public boolean isFirstBlock() {
		return getBeginBlock() == 1;
	}
	
	public boolean isLastBlock() {
		return getEndBlock() == getTotalPage();
	}
	
	
	// 물음표 뒤에 붙는 데이터 중 공통된 부분(size, column, keyword)에 대한 문자열 반환
	public String getQueryString() {
		return "size=" + size + "&column=" + getColumn() + "&keyword=" + getKeyword();
	}
	

	public boolean isCurrentPage(int i) {
		return page == i;
	}
	

	
	public int getPrevBlock() {
		return getBeginBlock() - 1;
	}
	
	public int getNextBlock(){
		return getEndBlock() + 1;
	}
}
