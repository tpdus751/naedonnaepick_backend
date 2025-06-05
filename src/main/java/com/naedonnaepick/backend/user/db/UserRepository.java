package com.naedonnaepick.backend.user.db;

import com.naedonnaepick.backend.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    // 이메일과 비밀번호로 사용자 조회
    UserEntity findByEmailAndPassword(String email, String password);
}