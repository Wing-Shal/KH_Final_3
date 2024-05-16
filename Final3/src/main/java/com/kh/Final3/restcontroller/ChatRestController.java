package com.kh.Final3.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.Final3.dao.ChatroomDao;
import com.kh.Final3.dao.MessageDao;
import com.kh.Final3.dto.ChatroomDto;
import com.kh.Final3.dto.EmpChatroomDto;
import com.kh.Final3.dto.MessageDto;
import com.kh.Final3.service.ChatroomService;
import com.kh.Final3.service.JwtService;
import com.kh.Final3.service.MessageService;
import com.kh.Final3.vo.ChatDataVO;
import com.kh.Final3.vo.LoginVO;
import com.kh.Final3.vo.MessageInfoVO;
import com.kh.Final3.vo.RecentMessageVO;

@CrossOrigin
@RestController
@RequestMapping("/chat")
public class ChatRestController {
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private ChatroomDao chatroomDao;
	
	@Autowired
	private JwtService jwtService;
	
    @Autowired
    private ChatroomService chatroomService;
	
	
	
	//채팅방의 전체 메세지
	@GetMapping("/{chatroomNo}")
	public List<MessageDto> list(@PathVariable int chatroomNo) {
		return messageDao.selectList(chatroomNo);
	}
	
	//채팅방 무한스크롤
	@GetMapping("/{chatroomNo}/page/{page}/size/{size}")
	public ChatDataVO list(@PathVariable int chatroomNo, @PathVariable int page, @PathVariable int size) {
	    int count = messageDao.count(chatroomNo);
	    int startRow = Math.max(0, count - (page * size));
	    
	    int endRow;
	    if(page != 1) {
	    	endRow = Math.max(0, count - ((page - 1) * size) - 1);
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
	

	
	//empNo가 속한 채팅방 목록
	@GetMapping("/list")
	public List<ChatroomDto> chatroomList(@RequestHeader("Authorization") String token) {
		LoginVO loginVO = jwtService.parse(token);
		int empNo = loginVO.getLoginId();
	    return chatroomDao.selectList(empNo);
	}
	
	//chatroomNo에 속한 empNo목록
	@GetMapping("/chatroomEmpList/{chatroomNo}")
	public List<EmpChatroomDto> empInChatroomList(@PathVariable int chatroomNo){
		return chatroomDao.selectListEmpByChatroom(chatroomNo);
	}
	
	//chatroomNo별 마지막 메시지
	@GetMapping("/recentMessageList/{chatroomNo}")
	public List<RecentMessageVO> lastMessageList(@PathVariable int chatroomNo){
		return chatroomDao.selectLastMessageList(chatroomNo);
	}
	
	
	

	//새로 채팅방을 열지, 만들지
    @PostMapping("/findOrCreate/{loginId}/{empNo}")
    public ChatroomDto findOrCreateChatroom(@RequestHeader("Authorization") String token,
//    public ChatroomDto findOrCreateChatroom(@PathVariable int loginId, 
    													@PathVariable int empNo) {
		LoginVO loginVO = jwtService.parse(token);
		int loginId = loginVO.getLoginId();
		
        return chatroomService.findOrCreateChatroom(loginId, empNo);
    }
    
    //사원 초대하기
    @PostMapping("/inviteEmp/{chatroomNo}/{empNo}")
    public ChatroomDto invetEmp(@PathVariable int chatroomNo, @PathVariable int empNo) {
    	return chatroomService.inviteEmp(chatroomNo, empNo);
    }
    
    //채팅방 이름 변경
	@PatchMapping("/")
	public ResponseEntity<?> editUnit(@RequestBody ChatroomDto chtroomDto) {
		boolean result = chatroomDao.editChatroomName(chtroomDto);
		if(result == false) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}
	
	//채팅방 나가기
	@DeleteMapping("/outChatroom/{chatroomNo}")
	public ResponseEntity<?> delete(@RequestHeader("Authorization") String token, @PathVariable int chatroomNo) {
//	public ResponseEntity<?> delete(@PathVariable int empNo, @PathVariable int chatroomNo) {
		LoginVO loginVO = jwtService.parse(token);
		int empNo = loginVO.getLoginId();
		boolean result = chatroomDao.outChatroom(empNo, chatroomNo);
		if(result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}
	
    @Autowired
    private MessageService messageService;

    //안읽은메세지
    @GetMapping("/unreadMessage")
    public ResponseEntity<List<MessageInfoVO>> getUnreadMessages() {
        List<MessageInfoVO> unreadMessages = messageService.retrieveUnreadMessages();
        return ResponseEntity.ok(unreadMessages);
    }
    
    //특정 채팅방에 대한 사용자의 안 읽은 메시지 수 조회
    @GetMapping("/unread/{chatroomNo}")
    public ResponseEntity<Integer> getUnreadMessageCount(@RequestHeader("Authorization") String token, @PathVariable int chatroomNo) {
        LoginVO loginVO = jwtService.parse(token);
        int empNo = loginVO.getLoginId();
        int unreadCount = messageService.countUnreadMessages(empNo, chatroomNo);
        return ResponseEntity.ok(unreadCount);
    }



	
}
