package com.naedonnaepick.backend.chat.websocket.controller;

import com.naedonnaepick.backend.chat.websocket.service.ChatMessageService;
import com.naedonnaepick.backend.chat.websocket.db.entity.ChatMessage;
import com.naedonnaepick.backend.chat.websocket.service.ChatMessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatWebSocketController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping("/chat/send/{roomNo}")
    @SendTo("/topic/chatroom/{roomNo}")
    public ChatMessage sendMessage(@DestinationVariable int roomNo, ChatMessage message) {
        chatMessageService.saveMessage(message);
        return message;
    }
}

