package com.naedonnaepick.backend.user.controller;

import com.naedonnaepick.backend.dto.LoginResponseDTO;
import com.naedonnaepick.backend.user.entity.UserEntity;
import com.naedonnaepick.backend.user.entity.UserTagsEntity;
import com.naedonnaepick.backend.user.service.UserService;
import com.naedonnaepick.backend.user.service.UserTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
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
}