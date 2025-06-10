package com.naedonnaepick.backend.restaurant.service;

import com.naedonnaepick.backend.restaurant.entity.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantService {

    List<RestaurantEntity> getRecommendedRestaurants(String location, int minPrice, int maxPrice);

    Page<RestaurantEntity> getRestaurantsBySearchText(String searchText, Pageable pageable);

    List<RestaurantEntity> findNearbyRestaurants(BigDecimal latitude, BigDecimal longitude);

    Page<RestaurantEntity> searchByTagWithDistance(String tag, BigDecimal lat, BigDecimal lng, int page, int size);

    Page<RestaurantEntity> searchByKeywordWithDistance(String keyword, BigDecimal lat, BigDecimal lng, int page, int size);
}
