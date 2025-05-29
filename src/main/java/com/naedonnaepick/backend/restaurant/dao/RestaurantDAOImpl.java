package com.naedonnaepick.backend.restaurant.dao;

import com.naedonnaepick.backend.restaurant.entity.RestaurantEntity;
import com.naedonnaepick.backend.restaurant.db.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestaurantDAOImpl implements RestaurantDAO {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantDAOImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<RestaurantEntity> findRestaurantsByLocationAndPrice(String location, int minPrice, int maxPrice) {
        return restaurantRepository.findRestaurantsByLocationAndPrice(location, minPrice, maxPrice);
    }
}