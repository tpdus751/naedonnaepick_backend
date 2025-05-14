package com.naedonnaepick.backend.chat.service;

import com.naedonnaepick.backend.chat.db.entity.ChatroomEntity;

import java.util.List;

public interface ChatService {


    List<ChatroomEntity> findAllChatrooms();

    void enterRoom(Long roomNo, String userId);
}
