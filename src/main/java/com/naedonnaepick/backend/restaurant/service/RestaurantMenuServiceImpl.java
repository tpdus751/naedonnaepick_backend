package com.naedonnaepick.backend.restaurant.service;

import com.naedonnaepick.backend.restaurant.dao.RestaurantMenuDAO;
import com.naedonnaepick.backend.restaurant.entity.RestaurantMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantMenuServiceImpl implements RestaurantMenuService {

    @Autowired
    private RestaurantMenuDAO restaurantMenuDAO;

    @Override
    public List<RestaurantMenu> getRestaurantMenus(int restaurantNo) {
        return restaurantMenuDAO.findByRestaurantNo(restaurantNo);
    }
}