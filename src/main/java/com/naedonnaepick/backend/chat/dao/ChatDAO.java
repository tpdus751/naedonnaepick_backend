package com.naedonnaepick.backend.chat.dao;

import com.naedonnaepick.backend.chat.db.ReportRepository;
import com.naedonnaepick.backend.chat.db.entity.ChatroomEntity;
import com.naedonnaepick.backend.chat.db.entity.ReportEntity;
import com.naedonnaepick.backend.chat.websocket.db.entity.ChatMessage;

import java.util.List;
public interface ChatDAO {


    List<ChatroomEntity> selectAllChatRooms();

    List<ChatMessage> selectAllChats(int roomNo);

    void insertReport(ReportEntity report);
}
