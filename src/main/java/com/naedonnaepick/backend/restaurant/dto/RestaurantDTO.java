package com.naedonnaepick.backend.restaurant.dto;

import com.naedonnaepick.backend.restaurant.entity.RestaurantEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {
    private int restaurantNo;
    private String name;

    public static RestaurantDTO fromEntity(RestaurantEntity entity) {
        return new RestaurantDTO(entity.getRestaurantNo(), entity.getName());
    }
}