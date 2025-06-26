package com.naedonnaepick.backend.chat.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatEnterRequestEntity {

    private Long roomNo;  // 요청 JSON의 roomNo

    private String email; // 요청 JSON의 userId

}
