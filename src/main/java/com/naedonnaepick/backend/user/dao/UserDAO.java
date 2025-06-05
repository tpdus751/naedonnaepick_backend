package com.naedonnaepick.backend.user.dao;

import com.naedonnaepick.backend.user.entity.UserEntity;

import java.util.Optional;

public interface UserDAO {
    UserEntity findUserByEmailAndPassword(String email, String password);
}