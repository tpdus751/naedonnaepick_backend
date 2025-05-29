// src/main/java/com/example/team_project_2_1/Member/dto/MemberLoginRequest.java
package com.naedonnaepick.backend.Member.dto;

import lombok.Data;

@Data
public class MemberLoginRequest {
    private String email;
    private String password;
}
