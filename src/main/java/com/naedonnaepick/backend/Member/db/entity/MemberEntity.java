// src/main/java/com/example/team_project_2_1/Member/db/entity/MemberEntity.java
package com.naedonnaepick.backend.Member.db.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberEntity {

    @Id
    private String email;

    private String password;

    private String nickname;

    private LocalDateTime createdAt;

    private int privacy_agreed;

    private String first_name;

    private String last_name;
}
