package com.naedonnaepick.backend.chat.service;

import com.naedonnaepick.backend.chat.dao.ChatDAO;
import com.naedonnaepick.backend.chat.db.entity.ChatroomEntity;
import com.naedonnaepick.backend.chat.db.entity.ReportEntity;
import com.naedonnaepick.backend.chat.websocket.db.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private ChatDAO chatDAO;

    @Autowired
    public ChatServiceImpl(ChatDAO chatDao) {
        this.chatDAO = chatDao;
    }

    @Override
    public List<ChatroomEntity> findAllChatrooms() {

        return chatDAO.selectAllChatRooms();
    }

    @Override
    public List<ChatMessage> findAllChats(int roomNo) {
        return chatDAO.selectAllChats(roomNo);
    }

    @Override
    public void submitReport(ReportEntity report) {
        chatDAO.insertReport(report);
    }
}
