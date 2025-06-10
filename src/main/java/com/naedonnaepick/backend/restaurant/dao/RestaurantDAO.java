package com.naedonnaepick.backend.restaurant.dao;

import com.naedonnaepick.backend.restaurant.entity.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantDAO {

    List<RestaurantEntity> findRestaurantsByLocationAndPrice(String location, int minPrice, int maxPrice);

    Page<RestaurantEntity> findRestaurantsBySearchText(String searchText, Pageable pageable);

    List<RestaurantEntity> findNearby(BigDecimal lat, BigDecimal lng);

    Page<RestaurantEntity> searchByTag(String tag, Pageable pageable);

    Page<RestaurantEntity> searchByKeyword(String keyword, Pageable pageable);
}