// src/main/java/com/example/team_project_2_1/Member/db/MemberRepository.java
package com.naedonnaepick.backend.Member.db;

import com.naedonnaepick.backend.Member.db.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    // 기본 CRUD(email PK 기준) 메서드 제공
}
