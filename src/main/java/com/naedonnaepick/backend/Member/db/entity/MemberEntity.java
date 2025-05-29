// src/main/java/com/example/team_project_2_1/Member/db/entity/MemberEntity.java
package com.naedonnaepick.backend.Member.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

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

    private String created_at;

    private int privacy_agreed;

    private String first_name;

    private String last_name;

}
