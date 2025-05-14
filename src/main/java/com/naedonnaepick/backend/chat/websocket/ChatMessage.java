package com.naedonnaepick.backend.chat.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    private Long roomNo;     // 채팅방 번호
    private String sender;   // 보낸 사람
    private String message;  // 메시지 내용

}
