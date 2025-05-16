package com.naedonnaepick.backend.chat.websocket.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer message_no;

    private Integer room_no;

    private String email;

    private String nickname;  // ✅ 닉네임 필드 추가

    private String content;

    private LocalDateTime sent_at;

    @PrePersist
    protected void onCreate() {
        if (this.sent_at == null) {
            this.sent_at = LocalDateTime.now();
        }
    }
}
