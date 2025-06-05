package com.naedonnaepick.backend.user.controller;

import com.naedonnaepick.backend.user.entity.UserEntity;
import com.naedonnaepick.backend.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 로그인 요청
    @PostMapping("/login")
    public UserEntity login(
            @RequestBody UserEntity userEntity
    ) { // @RequestBody 사용
        return userService.login(userEntity.getEmail(), userEntity.getPassword());
    }
}