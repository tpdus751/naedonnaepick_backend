package com.naedonnaepick.backend.user.dto;

import lombok.Data;
import java.util.Map;

@Data
public class RegisterRequestDTO {
    private String email;
    private String password;
    private String nickname;
    private String first_name;
    private String last_name;
    private int privacy_agreed;
    private Map<String, Integer> preferences; // 영어 key → 점수
}