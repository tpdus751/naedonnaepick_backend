package com.naedonnaepick.backend.chat.controller;

import com.naedonnaepick.backend.chat.entity.ChatEnterRequestEntity;
import com.naedonnaepick.backend.chat.db.entity.ChatroomEntity;
import com.naedonnaepick.backend.chat.service.ChatService;
import com.naedonnaepick.backend.chat.service.ChatServiceImpl;
import com.naedonnaepick.backend.chat.websocket.db.ChatMessageRepository;
import com.naedonnaepick.backend.chat.websocket.db.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://192.168.25.7:8081", "http://localhost:8081"})
@RestController
@RequestMapping("/api/chat")
public class ChatAPIController {

    private ChatService chatService;

    @Autowired
    ChatAPIController(ChatService chatService) {
        this.chatService = chatService;
    }

    // 채팅방 목록 가져오기
    @GetMapping("/chatrooms")
    public List<ChatroomEntity> findAllChatrooms() {

        return chatService.findAllChatrooms();
    }

    // ✅ 채팅방 입장 (필요시 입장 시도 기록 등 확장 가능)
    @PostMapping("/enter")
    public ResponseEntity<String> enterChatRoom(@RequestBody ChatEnterRequestEntity request) {
        // 현재 별도의 로직 없이 성공만 반환
        return ResponseEntity.ok("입장 성공");
    }

    // 채팅방 전체 대화 내역 송신
    @GetMapping("/history/{roomNo}")
    public List<ChatMessage> getChatHistory(@PathVariable int roomNo) {
        return chatService.findAllChats(roomNo);
    }

}
