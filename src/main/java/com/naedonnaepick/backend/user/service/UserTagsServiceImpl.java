package com.naedonnaepick.backend.user.service;

import com.naedonnaepick.backend.user.db.UserTagsRepository;
import com.naedonnaepick.backend.user.entity.UserTagsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserTagsServiceImpl implements UserTagsService{

    private final UserTagsRepository userTagsRepository;

    @Autowired
    public UserTagsServiceImpl(UserTagsRepository userTagsRepository) {
        this.userTagsRepository = userTagsRepository;
    }

    @Override
    public Optional<UserTagsEntity> getUserTagsByEmail(String email) {
        return userTagsRepository.findById(email);
    }
}
