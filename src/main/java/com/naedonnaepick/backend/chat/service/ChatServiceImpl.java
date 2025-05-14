package com.naedonnaepick.backend.chat.service;

import com.naedonnaepick.backend.chat.db.entity.ChatroomEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ChatServiceImpl implements ChatService {
    @Override
    public List<ChatroomEntity> findAllChatrooms() {

        List<ChatroomEntity> chatrooms = new ArrayList<>();

        ChatroomEntity chatroom1 = ChatroomEntity.builder()
                .room_no(1)
                .title("성남시에서 가장 핫한 음식점은?")
                .max_person_cnt(100)
                .created_at(Date.valueOf("2025-05-13"))
                .build();

        ChatroomEntity chatroom2 = ChatroomEntity.builder()
                .room_no(2)
                .title("성남시를 대표하는 메뉴는 이것!!")
                .max_person_cnt(100)
                .created_at(Date.valueOf("2025-05-14"))
                .build();

        chatrooms.add(chatroom1);
        chatrooms.add(chatroom2);

        return chatrooms;
    }

    @Override
    public void enterRoom(Long roomNo, String userId) {

    }
}
