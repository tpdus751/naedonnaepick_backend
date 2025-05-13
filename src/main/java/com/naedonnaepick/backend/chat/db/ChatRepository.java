package com.naedonnaepick.backend.chat.db;

import com.naedonnaepick.backend.chat.db.entity.ChatroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatroomEntity, String> {
}
