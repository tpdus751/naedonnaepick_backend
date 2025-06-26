package com.naedonnaepick.backend.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatLeaveRequestEntity {

    private String roomNo;
    private String email;


}
