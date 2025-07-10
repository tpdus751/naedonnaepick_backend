package com.naedonnaepick.backend.chat.websocket.service;

import com.naedonnaepick.backend.chat.websocket.db.entity.ChatMessage;
import com.naedonnaepick.backend.chat.websocket.dto.AnalysisResultDTO;

public interface ChatMessageService {
    void saveMessage(ChatMessage message);

    boolean existsTodayPositive(String email, int roomNo, int restaurantNo);

    AnalysisResultDTO anlysisSentiment(String content);

    void saveOrUpdatePositiveCount(String email, int roomNo, int restaurantNo);
}
