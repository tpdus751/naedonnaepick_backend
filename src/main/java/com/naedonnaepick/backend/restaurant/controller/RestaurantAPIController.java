package com.naedonnaepick.backend.restaurant.controller;

import com.naedonnaepick.backend.restaurant.entity.RestaurantEntity;
import com.naedonnaepick.backend.restaurant.entity.RestaurantMenu;
import com.naedonnaepick.backend.restaurant.service.RestaurantMenuService;
import com.naedonnaepick.backend.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
@CrossOrigin(origins = "*")
public class RestaurantAPIController {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RestaurantMenuService restaurantMenuService;


    @GetMapping("/recommended")
    public List<RestaurantEntity> sendRecommendedRestaurants(
            @RequestParam(name = "location") String location,
            @RequestParam(name = "minPrice", required = false, defaultValue = "0") int minPrice,
            @RequestParam(name = "maxPrice", required = false, defaultValue = "10000") int maxPrice
    ) {
        // Service 호출
        return restaurantService.getRecommendedRestaurants(location, minPrice, maxPrice);
    }

    @GetMapping("/menus")
    public List<RestaurantMenu> sendRestaurantMenus(
            @RequestParam(name = "restaurantNo") int restaurantNo
    ) {
        return restaurantMenuService.getRestaurantMenus(restaurantNo);
    }

}