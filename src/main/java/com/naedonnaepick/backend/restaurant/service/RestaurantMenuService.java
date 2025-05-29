package com.naedonnaepick.backend.restaurant.service;

import com.naedonnaepick.backend.restaurant.entity.RestaurantMenu;

import java.util.List;

public interface RestaurantMenuService {
    List<RestaurantMenu> getRestaurantMenus(int restaurantNo);
}
