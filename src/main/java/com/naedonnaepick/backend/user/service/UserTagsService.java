package com.naedonnaepick.backend.user.service;

import com.naedonnaepick.backend.user.entity.UserTagsEntity;

import java.util.Optional;

public interface UserTagsService {
    Optional<UserTagsEntity> getUserTagsByEmail(String email);

    void save(UserTagsEntity tagEntity);
}
