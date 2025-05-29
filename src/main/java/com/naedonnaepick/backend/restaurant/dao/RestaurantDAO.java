package com.naedonnaepick.backend.restaurant.dao;

import com.naedonnaepick.backend.restaurant.entity.RestaurantEntity;

import java.util.List;

public interface RestaurantDAO {

    List<RestaurantEntity> findRestaurantsByLocationAndPrice(String location, int minPrice, int maxPrice);

}