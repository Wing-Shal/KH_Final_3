package com.kh.Final3.vo;

import java.util.List;

import com.kh.Final3.dto.DocumentDto;
import com.kh.Final3.dto.ProjectDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//무한 스크롤을 위한 VO
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class DocumentDataVO {
	private List<DocumentDto> list;
	private int count;
	private boolean last;
}
