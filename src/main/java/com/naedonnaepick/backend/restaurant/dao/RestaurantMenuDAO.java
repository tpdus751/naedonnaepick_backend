package com.naedonnaepick.backend.restaurant.dao;

import com.naedonnaepick.backend.restaurant.entity.RestaurantMenu;

import java.util.List;

public interface RestaurantMenuDAO {
    List<RestaurantMenu> findByRestaurantNo(int restaurantNo);
}