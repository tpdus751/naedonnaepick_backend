// src/main/java/com/example/team_project_2_1/Member/dto/MemberRegisterRequest.java
package com.naedonnaepick.backend.Member.dto;


import com.naedonnaepick.backend.Member.db.entity.MemberEntity;
import lombok.Data;

@Data
public class MemberRegisterRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String nickname;

    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .nickname(nickname)
                .build();
    }
}
