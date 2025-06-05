// src/main/java/com/example/team_project_2_1/Member/db/UserTagRepository.java
package com.naedonnaepick.backend.Member.db;

import com.naedonnaepick.backend.Member.db.entity.UserTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTagRepository extends JpaRepository<UserTagEntity, String> {
}
