package com.naedonnaepick.backend.dto;

import com.naedonnaepick.backend.user.entity.UserEntity;
import com.naedonnaepick.backend.user.entity.UserTagsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private UserEntity user;
    private UserTagsEntity tags;
}