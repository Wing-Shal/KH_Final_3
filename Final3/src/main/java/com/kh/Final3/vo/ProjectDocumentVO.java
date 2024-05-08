package com.kh.Final3.vo;

import java.util.List;

import com.kh.Final3.dto.DocumentDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ProjectDocumentVO {
	
	private List<DocumentDto> list;
	private String projectName;
	

}
