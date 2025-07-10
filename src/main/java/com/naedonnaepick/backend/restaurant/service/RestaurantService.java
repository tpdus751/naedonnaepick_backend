package com.naedonnaepick.backend.restaurant.service;

import com.naedonnaepick.backend.restaurant.dto.RestaurantDTO;
import com.naedonnaepick.backend.restaurant.dto.RestaurantRecommendationDTO;
import com.naedonnaepick.backend.restaurant.dto.RestaurantWithDistanceDTO;
import com.naedonnaepick.backend.restaurant.entity.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RestaurantService {

    Optional<RestaurantEntity> findById(int id);

    Page<RestaurantWithDistanceDTO> findNearbyRestaurants(BigDecimal lat, BigDecimal lng, int page, int size);

    // 일반 키워드 검색 (거리 계산 및 정렬 포함)
    Page<RestaurantEntity> searchRestaurants(String searchText, BigDecimal lat, BigDecimal lng, int page, int size);

    // 태그 + 지역 검색 (거리 계산 및 정렬 포함)
    Page<RestaurantEntity> searchRestaurantsByTagAndDistrict(String tag, String district, BigDecimal lat, BigDecimal lng, int page, int size);

    // 기존의 `RestaurantAPIController`에서 사용되던 메서드들
    List<RestaurantWithDistanceDTO> getRecommendedRestaurants(String location, int minPrice, int maxPrice, BigDecimal lat, BigDecimal lng);

    Page<RestaurantWithDistanceDTO> getRecommendedRestaurants(
            String location, int minPrice, int maxPrice, BigDecimal lat, BigDecimal lng, Pageable pageable);

    Page<RestaurantEntity> getRestaurantsBySearchText(String searchText, Pageable pageable);

    // Controller에서 사용되는 이름과 일관성 있게 다시 정의
    Page<RestaurantEntity> searchByKeywordWithDistance(String keyword, BigDecimal lat, BigDecimal lng, int page, int size);

    Page<RestaurantEntity> searchByTagWithDistanceAndDistrict(String tag, String district, BigDecimal lat, BigDecimal lng, int page, int size);

    List<RestaurantRecommendationDTO> recommendByLocation(BigDecimal lat, BigDecimal lng, Map<String, Double> userTags, int minPrice, int maxPrice, String region);

    List<RestaurantDTO> searchRestaurantsByName(String query);
}