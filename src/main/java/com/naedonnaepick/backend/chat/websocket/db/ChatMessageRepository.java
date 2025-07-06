package com.naedonnaepick.backend.chat.websocket.db;

import com.naedonnaepick.backend.chat.websocket.db.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    @Query(
            value = "select * from chat_messages " +
                    "where room_no = :room_no " +
                    "ORDER BY sent_at ASC",
            nativeQuery = true
    )
    List<ChatMessage> findByRoomNo(@Param(value = "room_no") int roomNo);
}
