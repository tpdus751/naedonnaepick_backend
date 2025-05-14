package com.naedonnaepick.backend.chat.controller;

import com.naedonnaepick.backend.chat.entity.ChatEnterRequestEntity;
import com.naedonnaepick.backend.chat.db.entity.ChatroomEntity;
import com.naedonnaepick.backend.chat.service.ChatService;
import com.naedonnaepick.backend.chat.service.ChatServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://192.168.25.7:8081", "http://localhost:8081"})
@RestController
@RequestMapping("/api/chat")
public class ChatAPIController {

    private ChatService chatService = new ChatServiceImpl();

    // 채팅방 목록 가져오기
    @GetMapping("/chatrooms")
    public List<ChatroomEntity> findAllChatrooms() {

        return chatService.findAllChatrooms();
    }

    @PostMapping("/enter")
    public ResponseEntity<String> enterChatRoom(@RequestBody ChatEnterRequestEntity request) {
        chatService.enterRoom(request.getRoomNo(), request.getUserId());
        return ResponseEntity.ok("입장 성공");
    }

}
