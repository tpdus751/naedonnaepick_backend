package com.naedonnaepick.backend.chat.websocket.service;

import com.naedonnaepick.backend.chat.websocket.dao.ChatMessageDAO;
import com.naedonnaepick.backend.chat.websocket.dao.ChatMessageDAOImpl;
import com.naedonnaepick.backend.chat.websocket.db.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageDAO chatMessageDAO;

    @Autowired
    public ChatMessageServiceImpl(ChatMessageDAO chatMessageDAO) {
        this.chatMessageDAO = chatMessageDAO;
    }

    @Override
    public void saveMessage(ChatMessage message) {
        chatMessageDAO.saveMessage(message);
    }
}

