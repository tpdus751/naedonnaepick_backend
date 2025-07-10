package com.naedonnaepick.backend.chat.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisResultDTO {
    private String label;         // "positive", "neutral", "negative"
    private double probability;   // 신뢰도 (0.0 ~ 1.0)

    // 도우미 메서드: 긍정 여부 판별
    public boolean isPositive() {
        return "positive".equalsIgnoreCase(label);
    }

    public boolean isNegative() {
        return "negative".equalsIgnoreCase(label);
    }

    public boolean isNeutral() {
        return "neutral".equalsIgnoreCase(label);
    }

    // 한글 감정 변환
    public String getSentiment() {
        switch (label.toLowerCase()) {
            case "positive": return "긍정";
            case "neutral": return "중립";
            case "negative": return "부정";
            default: return "알 수 없음";
        }
    }
}