package com.naedonnaepick.backend.restaurant.dao;

import com.naedonnaepick.backend.restaurant.db.RestaurantRepository;
import com.naedonnaepick.backend.restaurant.entity.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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

    @Override
    public Page<RestaurantEntity> findRestaurantsBySearchText(String name, Pageable pageable) {
        return restaurantRepository.findRestaurantsBySearchText(name, pageable);
    }

    @Override
    public List<RestaurantEntity> findNearby(BigDecimal lat, BigDecimal lng) {
        return restaurantRepository.findNearbyWithMenus(lat, lng);
    }

    @Override
    public Page<RestaurantEntity> searchByTag(String tag, Pageable pageable) {
        return restaurantRepository.searchByTag(tag, pageable);
    }

    @Override
    public Page<RestaurantEntity> searchByKeyword(String keyword, Pageable pageable) {
        return restaurantRepository.searchByKeyword(keyword, pageable);
    }

}