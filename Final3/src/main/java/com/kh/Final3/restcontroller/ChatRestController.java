package com.kh.Final3.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.Final3.dao.ChatroomDao;
import com.kh.Final3.dao.MessageDao;
import com.kh.Final3.dto.ChatroomDto;
import com.kh.Final3.dto.MessageDto;
import com.kh.Final3.vo.ChatDataVO;

@CrossOrigin
@RestController
@RequestMapping("/chat")
public class ChatRestController {
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private ChatroomDao chatroomDao;
	
	
	
	@GetMapping("/{chatroomNo}")
	public List<MessageDto> list(@PathVariable int chatroomNo) {
		return messageDao.selectList(chatroomNo);
	}
	
	@GetMapping("/{chatroomNo}/page/{page}/size/{size}")
	public ChatDataVO list(@PathVariable int chatroomNo, @PathVariable int page, @PathVariable int size) {
	    int count = messageDao.count(chatroomNo);
	    int startRow = Math.max(0, count - (page * size));
	    
	    int endRow;
	    
	    if(page != 1) {
	    	endRow = Math.max(0, count - ((page - 1) * size) - 1); //
	    }
	    else {
	    	endRow = Math.max(0, count - ((page - 1) * size));
	    }

	    // 메시지 목록 조회
	    List<MessageDto> list = messageDao.selectListByPaging(chatroomNo, startRow, endRow);

	    // 마지막 페이지 여부 계산
	    boolean last = page * size >= count;

	    return ChatDataVO.builder()
	                     .list(list)
	                     .count(count)
	                     .last(last)
	                     .build();
	}
	

	
	
	@GetMapping("/list/{empNo}")
	public List<ChatroomDto> chatroomList(@PathVariable("empNo") int empNo) {
	    return chatroomDao.selectList(empNo);
	}
	



	
}
