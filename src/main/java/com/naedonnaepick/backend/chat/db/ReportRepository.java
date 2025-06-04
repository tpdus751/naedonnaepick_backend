package com.naedonnaepick.backend.chat.db;

import com.naedonnaepick.backend.chat.db.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Integer> {
}
