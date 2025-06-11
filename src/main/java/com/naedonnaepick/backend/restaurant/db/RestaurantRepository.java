package com.naedonnaepick.backend.restaurant.db;

import com.naedonnaepick.backend.restaurant.dto.RestaurantWithDistanceDTO;
import com.naedonnaepick.backend.restaurant.entity.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Integer> {

    @Query(value = "SELECT r.restaurant_no as restaurantNo, r.name as name, r.address as address, " +
            "CASE " +
            "   WHEN r.latitude IS NOT NULL AND r.longitude IS NOT NULL THEN " +
            "       (6371 * acos(LEAST(1, " +
            "           cos(radians(:lat)) * cos(radians(r.latitude)) * " +
            "           cos(radians(r.longitude) - radians(:lng)) + " +
            "           sin(radians(:lat)) * sin(radians(r.latitude))" +
            "       ))) " +
            "   ELSE 999999999999.0 " +
            "END as distance " +
            "FROM restaurants r " +
            "JOIN restaurant_menus m ON r.restaurant_no = m.restaurant_no " +
            "WHERE r.address LIKE %:location% " +
            "AND m.price BETWEEN :minPrice AND :maxPrice " +
            "GROUP BY r.restaurant_no, r.name, r.address, r.latitude, r.longitude " +
            "ORDER BY distance ASC",
            nativeQuery = true)
    List<RestaurantWithDistanceDTO> findRestaurantsByLocationPriceAndDistance(
            @Param("location") String location,
            @Param("minPrice") int minPrice,
            @Param("maxPrice") int maxPrice,
            @Param("lat") BigDecimal lat,
            @Param("lng") BigDecimal lng
    );

    @Query(value = "SELECT r.restaurant_no as restaurantNo, r.name as name, r.address as address, " +
            "CASE " +
            "   WHEN r.latitude IS NOT NULL AND r.longitude IS NOT NULL THEN " +
            "       (6371 * acos(LEAST(1, " +
            "           cos(radians(:lat)) * cos(radians(r.latitude)) * " +
            "           cos(radians(r.longitude) - radians(:lng)) + " +
            "           sin(radians(:lat)) * sin(radians(r.latitude))" +
            "       ))) " +
            "   ELSE 999999999999.0 " +
            "END as distance " +
            "FROM restaurants r " +
            "JOIN restaurant_menus m ON r.restaurant_no = m.restaurant_no " +
            "WHERE r.address LIKE %:location% " +
            "AND m.price BETWEEN :minPrice AND :maxPrice " +
            "GROUP BY r.restaurant_no, r.name, r.address, r.latitude, r.longitude " +
            "ORDER BY distance ASC",
            countQuery = "SELECT COUNT(DISTINCT r.restaurant_no) " +
                    "FROM restaurants r " +
                    "JOIN restaurant_menus m ON r.restaurant_no = m.restaurant_no " +
                    "WHERE r.address LIKE %:location% " +
                    "AND m.price BETWEEN :minPrice AND :maxPrice ",
            nativeQuery = true)
    Page<RestaurantWithDistanceDTO> findRestaurantsByLocationPriceAndDistancePageable(
            @Param("location") String location,
            @Param("minPrice") int minPrice,
            @Param("maxPrice") int maxPrice,
            @Param("lat") BigDecimal lat,
            @Param("lng") BigDecimal lng,
            Pageable pageable
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


    // 홈 화면 '가까운 음식점' 상위 4개용 또는 특정 목적용 쿼리
    @Query(value = "SELECT r.*, " +
            "CASE " +
            "   WHEN r.latitude IS NOT NULL AND r.longitude IS NOT NULL THEN " +
            "       (6371 * acos(cos(radians(:lat)) * cos(radians(r.latitude)) * " +
            "       cos(radians(r.longitude) - radians(:lng)) + sin(radians(:lat)) * sin(radians(r.latitude)))) " + // ✅ 이 부분 수정: sin(radians(:lat)) * sin(radians(r.latitude))))
            "   ELSE 999999999999.0 " +
            "END AS distance " +
            "FROM restaurants r " +
            "JOIN restaurant_menus m ON r.restaurant_no = m.restaurant_no " +
            "GROUP BY r.restaurant_no " +
            "HAVING distance < 5 " + // 5km 이내로 제한
            "ORDER BY distance ASC " +
            "LIMIT 20", nativeQuery = true)
    List<RestaurantEntity> findNearbyWithMenus(@Param("lat") BigDecimal lat, @Param("lng") BigDecimal lng);

    @Query(value = "SELECT r.restaurant_no as restaurantNo, r.name as name, r.address as address, " +
            "CASE " +
            "   WHEN r.latitude IS NOT NULL AND r.longitude IS NOT NULL THEN " +
            "       (6371000 * acos(LEAST(1, " +  // ✅ 6371000 = 지구 반지름 (m 단위)
            "           cos(radians(:lat)) * cos(radians(r.latitude)) * " +
            "           cos(radians(r.longitude) - radians(:lng)) + " +
            "           sin(radians(:lat)) * sin(radians(r.latitude))" +
            "       ))) " +
            "   ELSE 999999999999.0 " +
            "END as distance " +
            "FROM restaurants r " +
            "JOIN restaurant_menus m ON r.restaurant_no = m.restaurant_no " +
            "GROUP BY r.restaurant_no, r.name, r.address, r.latitude, r.longitude " +
            "ORDER BY distance ASC",
            countQuery = "SELECT COUNT(DISTINCT r.restaurant_no) FROM restaurants r JOIN restaurant_menus m ON r.restaurant_no = m.restaurant_no",
            nativeQuery = true)
    Page<RestaurantWithDistanceDTO> findAllRestaurantsWithDistance(
            @Param("lat") BigDecimal lat,
            @Param("lng") BigDecimal lng,
            Pageable pageable
    );



    // NearbyListScreen 에서 모든 음식점을 거리순으로 가져올 때 사용
    @Query(value = "SELECT r.*, " +
            "CASE " +
            "   WHEN r.latitude IS NOT NULL AND r.longitude IS NOT NULL THEN " +
            "       (6371 * acos(cos(radians(:lat)) * cos(radians(r.latitude)) * " +
            "       cos(radians(r.longitude) - radians(:lng)) + sin(radians(:lat)) * sin(radians(r.latitude)))) " + // ✅ 이 부분 수정: sin(radians(:lat)) * sin(radians(r.latitude))))
            "   ELSE 999999999999.0 " +
            "END AS distance " +
            "FROM restaurants r " +
            "JOIN restaurant_menus m ON r.restaurant_no = m.restaurant_no " + // 메뉴가 있는 식당만 포함
            "GROUP BY r.restaurant_no " + // 중복 식당 제거
            "ORDER BY distance ASC", // 거리순으로 정렬 (큰 값은 뒤로 밀림)
            countQuery = "SELECT COUNT(DISTINCT r.restaurant_no) FROM restaurants r JOIN restaurant_menus m ON r.restaurant_no = m.restaurant_no",
            nativeQuery = true)
    Page<RestaurantEntity> findAllRestaurantsOrderedByDistance(
            @Param("lat") BigDecimal lat,
            @Param("lng") BigDecimal lng,
            Pageable pageable
    );

    @Query(value = "SELECT DISTINCT r FROM RestaurantEntity r " +
            "JOIN r.restaurantMenus m " +
            "WHERE r.name LIKE %:keyword% OR m.menu LIKE %:keyword%",
            countQuery = "SELECT COUNT(DISTINCT r.restaurantNo) FROM RestaurantEntity r " +
                    "JOIN r.restaurantMenus m " +
                    "WHERE r.name LIKE %:keyword% OR m.menu LIKE %:keyword%")
    Page<RestaurantEntity> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT DISTINCT r FROM RestaurantEntity r " +
            "JOIN r.restaurantMenus m " +
            "WHERE (r.name LIKE %:tag% OR m.menu LIKE %:tag%) " +
            "AND r.address LIKE %:district%",
            countQuery = "SELECT COUNT(DISTINCT r.restaurantNo) FROM RestaurantEntity r " +
                    "JOIN r.restaurantMenus m " +
                    "WHERE (r.name LIKE %:tag% OR m.menu LIKE %:tag%) " +
                    "AND r.address LIKE %:district%")
    Page<RestaurantEntity> searchByTagWithDistrict(
            @Param("tag") String tag,
            @Param("district") String district,
            Pageable pageable
    );
}