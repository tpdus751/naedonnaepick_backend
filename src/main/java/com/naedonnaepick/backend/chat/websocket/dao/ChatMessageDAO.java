package com.naedonnaepick.backend.chat.websocket.dao;

import com.naedonnaepick.backend.chat.websocket.db.entity.ChatMessage;

public interface ChatMessageDAO {

    public void saveMessage(ChatMessage message);

}
