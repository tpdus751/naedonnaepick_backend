package com.naedonnaepick.backend.restaurant.db;

import com.naedonnaepick.backend.restaurant.entity.RestaurantEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Integer> {

    @Query("SELECT r FROM RestaurantEntity r " +
            "JOIN r.restaurantMenus m " +
            "WHERE r.address LIKE %:location% " +
            "AND m.price BETWEEN :minPrice AND :maxPrice " +
            "GROUP BY r.restaurantNo")
    List<RestaurantEntity> findRestaurantsByLocationAndPrice(
            @Param("location") String location,
            @Param("minPrice") int minPrice,
            @Param("maxPrice") int maxPrice
    );

    @Query("SELECT r FROM RestaurantEntity r " +
            "JOIN r.restaurantMenus m " +
            "WHERE r.name LIKE %:searchText% " +
            "OR m.menu LIKE %:searchText% " +
            "GROUP BY r.restaurantNo")
    List<RestaurantEntity> findRestaurantsBySearchText(
            @Param("searchText") String searchText,
            Pageable pageable
    );

}