package com.kh.Final3.vo;

import java.util.List;

import com.kh.Final3.dto.MessageDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//채팅 무한 스크롤을 위한 VO
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ChatDataVO {
	private List<MessageDto> list;
	private int count; 
	private boolean last;
}
