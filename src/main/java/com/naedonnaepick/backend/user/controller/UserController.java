package com.naedonnaepick.backend.user.controller;

import com.naedonnaepick.backend.dto.LoginResponseDTO;
import com.naedonnaepick.backend.user.dto.RegisterRequestDTO;
import com.naedonnaepick.backend.user.entity.UserEntity;
import com.naedonnaepick.backend.user.entity.UserTagsEntity;
import com.naedonnaepick.backend.user.service.UserService;
import com.naedonnaepick.backend.user.service.UserTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:8081")
public class UserController {

    private final UserService userService;
    private final UserTagsService userTagsService;

    @Autowired
    public UserController(UserService userService, UserTagsService userTagsService) {
        this.userService = userService;
        this.userTagsService = userTagsService;
    }

    // 로그인 요청
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEntity userEntity) {
        UserEntity user = userService.login(userEntity.getEmail(), userEntity.getPassword());
        if (user == null) {
            return ResponseEntity.status(401).body("이메일 또는 비밀번호가 잘못되었습니다.");
        }

        Optional<UserTagsEntity> tagsOpt = userTagsService.getUserTagsByEmail(user.getEmail());
        if (tagsOpt.isEmpty()) {
            return ResponseEntity.status(404).body("사용자 태그 정보가 없습니다.");
        }

        LoginResponseDTO response = new LoginResponseDTO(user, tagsOpt.get());
        return ResponseEntity.ok(response);
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO dto) {
        // 이메일 중복 확인
        if (userService.existsByEmail(dto.getEmail())) {
            return ResponseEntity.status(409).body("이미 등록된 이메일입니다.");
        }

        // 1. UserEntity 저장
        UserEntity user = UserEntity.builder()
                .email(dto.getEmail())
                .password(dto.getPassword()) // 암호화 필요 시 수정
                .nickname(dto.getNickname())
                .first_name(dto.getFirst_name())
                .last_name(dto.getLast_name())
                .privacy_agreed(dto.getPrivacy_agreed())
                .build();
        userService.save(user);

        // 2. UserTagsEntity 저장 (Map에서 꺼내기)
        Map<String, Integer> tags = dto.getPreferences();
        UserTagsEntity tagEntity = UserTagsEntity.builder()
                .email(dto.getEmail())
                .spicy(tags.getOrDefault("spicy", 3))
                .value_for_money(tags.getOrDefault("value_for_money", 3))
                .kindness(tags.getOrDefault("kindness", 3))
                .cleanliness(tags.getOrDefault("cleanliness", 3))
                .atmosphere(tags.getOrDefault("atmosphere", 3))
                .large_portions(tags.getOrDefault("large_portions", 3))
                .tasty(tags.getOrDefault("tasty", 3))
                .waiting(tags.getOrDefault("waiting", 3))
                .sweet(tags.getOrDefault("sweet", 3))
                .salty(tags.getOrDefault("salty", 3))
                .savory(tags.getOrDefault("savory", 3))
                .freshness(tags.getOrDefault("freshness", 3))
                .solo_dining(tags.getOrDefault("solo_dining", 3))
                .trendy(tags.getOrDefault("trendy", 3))
                .parking(tags.getOrDefault("parking", 3))
                .build();
        userTagsService.save(tagEntity);

        return ResponseEntity.ok("회원가입 성공");
    }
}