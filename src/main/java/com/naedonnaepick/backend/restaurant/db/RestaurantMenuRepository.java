package com.naedonnaepick.backend.restaurant.db;

import com.naedonnaepick.backend.restaurant.entity.RestaurantMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenu, Integer> {
    // 레스토랑 번호(restaurantNo)에 따라 메뉴 목록 조회
    @Query(value = "SELECT m FROM RestaurantMenu m WHERE m.restaurantEntity.restaurantNo = :restaurantNo")
    List<RestaurantMenu> findMenusByRestaurantNo(@Param("restaurantNo") int restaurantNo);
}