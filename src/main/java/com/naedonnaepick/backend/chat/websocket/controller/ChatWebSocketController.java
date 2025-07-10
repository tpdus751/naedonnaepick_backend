package com.naedonnaepick.backend.chat.websocket.controller;

import com.naedonnaepick.backend.chat.websocket.dto.AnalysisResultDTO;
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
        System.out.println(message.toString());

        if (message.getRestaurant_name() != null && message.getRestaurant_detail() != null) {
            // 오늘 날짜의 해당 사용자+음식점+방번호+년월로 positive 기록 조회
            boolean alreadyExists = chatMessageService.existsTodayPositive(
                    message.getEmail(), roomNo, message.getRestaurant_detail()
            );

            if (!alreadyExists) {
                AnalysisResultDTO result = chatMessageService.anlysisSentiment(message.getContent());
                if ("긍정".equals(result.getSentiment())) {
                    chatMessageService.saveOrUpdatePositiveCount(
                            message.getEmail(), roomNo, message.getRestaurant_detail()
                    );
                }
            }
        }

        chatMessageService.saveMessage(message);
        return message;
    }

}

