package com.naedonnaepick.backend.chat.websocket.dao;

import com.naedonnaepick.backend.chat.websocket.db.ChatMessageRepository;
import com.naedonnaepick.backend.chat.websocket.db.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ChatMessageDAOImpl implements ChatMessageDAO {

    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatMessageDAOImpl(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public void saveMessage(ChatMessage message) {
        chatMessageRepository.save(message);
    }
}

