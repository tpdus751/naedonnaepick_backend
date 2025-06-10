package com.naedonnaepick.backend.restaurant.db;

import com.naedonnaepick.backend.restaurant.entity.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
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

    @Query(value = "SELECT DISTINCT r FROM RestaurantEntity r " +
            "JOIN r.restaurantMenus m " +
            "WHERE r.name LIKE %:searchText% " +
            "OR m.menu LIKE %:searchText%",
            countQuery = "SELECT COUNT(DISTINCT r) FROM RestaurantEntity r " +
                    "JOIN r.restaurantMenus m " +
                    "WHERE r.name LIKE %:searchText% " +
                    "OR m.menu LIKE %:searchText%")
    Page<RestaurantEntity> findRestaurantsBySearchText(@Param("searchText") String searchText, Pageable pageable);


    @Query(value = "SELECT r.*, " +
            "(6371 * acos(cos(radians(:lat)) * cos(radians(r.latitude)) * " +
            "cos(radians(r.longitude) - radians(:lng)) + sin(radians(:lat)) * sin(radians(r.latitude)))) AS distance " +
            "FROM restaurants r " +
            "JOIN restaurant_menus m ON r.restaurant_no = m.restaurant_no " +
            "GROUP BY r.restaurant_no " +
            "HAVING distance < 5 " +
            "ORDER BY distance ASC " +
            "LIMIT 20", nativeQuery = true)
    List<RestaurantEntity> findNearbyWithMenus(@Param("lat") BigDecimal lat, @Param("lng") BigDecimal lng);

    @Query(value = "SELECT r.* FROM restaurants r " +
            "JOIN restaurant_menus m ON r.restaurant_no = m.restaurant_no " +
            "WHERE (r.name LIKE %:tag% OR m.menu LIKE %:tag%)",
            countQuery = "SELECT COUNT(*) FROM restaurants r " +
                    "JOIN restaurant_menus m ON r.restaurant_no = m.restaurant_no " +
                    "WHERE (r.name LIKE %:tag% OR m.menu LIKE %:tag%)",
            nativeQuery = true)
    Page<RestaurantEntity> searchByTag(@Param("tag") String tag, Pageable pageable);


    @Query(value = "SELECT r.* FROM restaurants r " +
            "JOIN restaurant_menus m ON r.restaurant_no = m.restaurant_no " +
            "WHERE (r.name LIKE %:keyword% OR m.menu LIKE %:keyword%)",
            countQuery = "SELECT COUNT(*) FROM restaurants r " +
                    "JOIN restaurant_menus m ON r.restaurant_no = m.restaurant_no " +
                    "WHERE (r.name LIKE %:keyword% OR m.menu LIKE %:keyword%)",
            nativeQuery = true)
    Page<RestaurantEntity> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);


}