package com.kh.Final3.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.Final3.dao.MessageDao;
import com.kh.Final3.dto.MessageDto;

@CrossOrigin
@RestController
@RequestMapping("/chat")
public class ChatRestController {
	
	@Autowired
	private MessageDao messageDao;
	
	@GetMapping("/{chatroomNo}")
	public List<MessageDto> list(@PathVariable int chatroomNo) {
		return messageDao.selectList(chatroomNo);
	}
}
