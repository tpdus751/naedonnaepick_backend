package com.naedonnaepick.backend.user.service;

import com.naedonnaepick.backend.user.dao.UserDAO;
import com.naedonnaepick.backend.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserEntity login(String email, String password) {
        // 비즈니스 로직 (예: 비밀번호 해시 검증 등을 추가할 수 있음)
        return userDAO.findUserByEmailAndPassword(email, password);
    }
}