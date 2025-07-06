package com.naedonnaepick.backend.chat.dao;

import com.naedonnaepick.backend.chat.db.ChatRepository;
import com.naedonnaepick.backend.chat.db.ReportRepository;
import com.naedonnaepick.backend.chat.db.entity.ChatroomEntity;
import com.naedonnaepick.backend.chat.db.entity.ReportEntity;
import com.naedonnaepick.backend.chat.websocket.db.ChatMessageRepository;
import com.naedonnaepick.backend.chat.websocket.db.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatDAOImpl implements ChatDAO {

    private ChatRepository chatRepository;
    private ChatMessageRepository chatMessageRepository;
    private ReportRepository reportRepository;

    @Autowired
    public ChatDAOImpl(ChatRepository chatRepository, ChatMessageRepository chatMessageRepository, ReportRepository reportRepository) {
        this.chatRepository = chatRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.reportRepository = reportRepository;
    }

    @Override
    public List<ChatroomEntity> selectAllChatRooms() {
        return chatRepository.findAll();
    }

    @Override
    public List<ChatMessage> selectAllChats(int roomNo) {
        return chatMessageRepository.findByRoomNo(roomNo);
    }

    @Override
    public void insertReport(ReportEntity report) {
        reportRepository.save(report);
    }
}
