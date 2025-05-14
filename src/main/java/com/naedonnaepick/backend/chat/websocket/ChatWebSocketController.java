package com.naedonnaepick.backend.chat.websocket;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

    // 클라이언트가 /app/chat/send 로 보낸 메시지 처리
    @MessageMapping("/chat/send/{roomNo}")
    @SendTo("/topic/chatroom/{roomNo}")  // 모든 참여자에게 메시지 전달
    public ChatMessage sendMessage(@DestinationVariable int roomNo, ChatMessage message) {
        System.out.println(message);
        return message;  // 클라이언트로 메시지 반환 (브로드캐스팅)
    }
}
