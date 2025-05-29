package com.naedonnaepick.backend.restaurant.service;

import com.naedonnaepick.backend.restaurant.entity.RestaurantEntity;

import java.util.List;

public interface RestaurantService {

    List<RestaurantEntity> getRecommendedRestaurants(String location, int minPrice, int maxPrice);
}
