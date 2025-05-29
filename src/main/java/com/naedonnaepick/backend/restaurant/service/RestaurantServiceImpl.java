package com.naedonnaepick.backend.restaurant.service;

import com.naedonnaepick.backend.restaurant.dao.RestaurantDAO;
import com.naedonnaepick.backend.restaurant.entity.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantDAO restaurantDAO;

    @Override
    public List<RestaurantEntity> getRecommendedRestaurants(String location, int minPrice, int maxPrice) {
        // Repository 메서드를 호출하여 필터링된 데이터 반환
        return restaurantDAO.findRestaurantsByLocationAndPrice(location, minPrice, maxPrice);
    }
}