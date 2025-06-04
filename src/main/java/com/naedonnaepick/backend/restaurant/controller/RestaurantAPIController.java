package com.naedonnaepick.backend.restaurant.controller;

import com.naedonnaepick.backend.restaurant.entity.RestaurantEntity;
import com.naedonnaepick.backend.restaurant.entity.RestaurantMenu;
import com.naedonnaepick.backend.restaurant.service.RestaurantMenuService;
import com.naedonnaepick.backend.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            @RequestParam(name = "minPrice") int minPrice,
            @RequestParam(name = "maxPrice") int maxPrice
    ) {
        // Service 호출
        return restaurantService.getRecommendedRestaurants(location, minPrice, maxPrice);
    }

    @GetMapping("/search")
    public List<RestaurantEntity> sendSearchRestaurants(
            @RequestParam(name = "searchText") String searchText,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return restaurantService.getRestaurantsBySearchText(searchText, pageable).getContent();
    }


    @GetMapping("/menus")
    public List<RestaurantMenu> sendRestaurantMenus(
            @RequestParam(name = "restaurantNo") int restaurantNo
    ) {
        return restaurantMenuService.getRestaurantMenus(restaurantNo);
    }

}