package com.naedonnaepick.backend.user.db;

import com.naedonnaepick.backend.user.entity.UserTagsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTagsRepository extends JpaRepository<UserTagsEntity, String> {
}
