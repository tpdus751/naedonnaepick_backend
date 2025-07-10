package com.naedonnaepick.backend.chat.websocket.repository;

import com.naedonnaepick.backend.chat.websocket.entity.PositiveRestaurantManageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PositiveRestaurantManageRepository extends JpaRepository<PositiveRestaurantManageEntity, Integer> {

    // ✅ 필드명과 정확히 일치하도록 수정
    boolean existsBySenderEmailAndRoomNoAndRestaurantNoAndVoteMonth(
            String senderEmail, int roomNo, int restaurantNo, LocalDate voteMonth);

    Optional<PositiveRestaurantManageEntity> findBySenderEmailAndRoomNoAndRestaurantNoAndVoteMonth(
            String senderEmail, int roomNo, int restaurantNo, LocalDate voteMonth);

    @Query("SELECT r.name, r.address, p.positive_count " +
            "FROM PositiveRestaurantManageEntity p " +
            "JOIN RestaurantEntity r ON p.restaurantNo = r.restaurantNo " +
            "WHERE p.roomNo = :roomNo AND FUNCTION('DATE_FORMAT', p.voteMonth, '%Y-%m') = :voteMonth " +
            "ORDER BY p.positive_count DESC")
    List<Object[]> findTopVotedRestaurants(@Param("roomNo") int roomNo,
                                           @Param("voteMonth") String voteMonth,
                                           Pageable pageable);
}