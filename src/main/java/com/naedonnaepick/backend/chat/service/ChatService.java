package com.naedonnaepick.backend.chat.service;

import com.naedonnaepick.backend.chat.db.entity.ChatroomEntity;
import com.naedonnaepick.backend.chat.db.entity.ReportEntity;
import com.naedonnaepick.backend.chat.dto.TopVotedRestaurantDTO;
import com.naedonnaepick.backend.chat.websocket.db.entity.ChatMessage;

import java.util.List;

public interface ChatService {


    List<ChatroomEntity> findAllChatrooms();

    List<ChatMessage> findAllChats(int roomNo);

    void submitReport(ReportEntity report);

    List<TopVotedRestaurantDTO> getTopVotedRestaurants(int roomNo);
}
