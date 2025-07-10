package com.naedonnaepick.backend.user.service;

import com.naedonnaepick.backend.user.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    UserEntity login(String email, String password);

    boolean existsByEmail(String email);

    void save(UserEntity user);
}