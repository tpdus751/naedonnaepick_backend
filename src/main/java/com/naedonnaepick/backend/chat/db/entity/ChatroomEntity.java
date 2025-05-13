package com.naedonnaepick.backend.chat.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "chatrooms")
public class ChatroomEntity {

    @Id
    private int room_no;

    private String title;

    private int max_person_cnt;

    private Date created_at;

    private Date terminated_at;

}
