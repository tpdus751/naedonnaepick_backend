package com.naedonnaepick.backend.chat.dao;

import com.naedonnaepick.backend.chat.db.ChatRepository;
import com.naedonnaepick.backend.chat.db.entity.ChatroomEntity;
import com.naedonnaepick.backend.chat.websocket.db.ChatMessageRepository;
import com.naedonnaepick.backend.chat.websocket.db.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatDAOImpl implements ChatDAO {

    private ChatRepository chatRepository;
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatDAOImpl(ChatRepository chatRepository, ChatMessageRepository chatMessageRepository) {
        this.chatRepository = chatRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public List<ChatroomEntity> selectAllChatRooms() {
        return chatRepository.findAll();
    }

    @Override
    public List<ChatMessage> selectAllChats(int roomNo) {
        return chatMessageRepository.findByRoomNo(roomNo);
    }
}
