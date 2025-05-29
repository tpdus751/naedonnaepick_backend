// src/main/java/com/example/team_project_2_1/Member/dto/MemberResponse.java
package com.naedonnaepick.backend.Member.dto;

import com.naedonnaepick.backend.Member.db.entity.MemberEntity;
import lombok.Data;

@Data
public class MemberResponse {
    private String email;
    private String firstName;
    private String lastName;
    private String nickname;

    public static MemberResponse from(MemberEntity e) {
        MemberResponse r = new MemberResponse();
        r.setEmail(e.getEmail());
        r.setFirstName(e.getFirstName());
        r.setLastName(e.getLastName());
        r.setNickname(e.getNickname());
        return r;
    }
}
