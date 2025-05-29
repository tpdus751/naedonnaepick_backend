package com.naedonnaepick.backend.restaurant.dao;

import com.naedonnaepick.backend.restaurant.db.RestaurantMenuRepository;
import com.naedonnaepick.backend.restaurant.entity.RestaurantMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestaurantMenuDAOImpl implements RestaurantMenuDAO {

    private final RestaurantMenuRepository restaurantMenuRepository;

    @Autowired
    public RestaurantMenuDAOImpl(RestaurantMenuRepository restaurantMenuRepository) {
        this.restaurantMenuRepository = restaurantMenuRepository;
    }

    @Override
    public List<RestaurantMenu> findByRestaurantNo(int restaurantNo) {
        // 수정된 메서드 호출
        return restaurantMenuRepository.findMenusByRestaurantNo(restaurantNo);
    }
}