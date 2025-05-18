package com.naedonnaepick.backend.chat.websocket.service;

import com.naedonnaepick.backend.chat.websocket.db.entity.ChatMessage;

public interface ChatMessageService {
    void saveMessage(ChatMessage message);
}
