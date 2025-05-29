package com.naedonnaepick.backend.chat.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_rooms")  // ✅ 실제 테이블 이름 정확히 명시
public class ChatroomEntity {

    @Id
    private Integer room_no;

    private String title;

    private int max_person_cnt;

    private Date created_at;

    private Date terminated_at;

}
