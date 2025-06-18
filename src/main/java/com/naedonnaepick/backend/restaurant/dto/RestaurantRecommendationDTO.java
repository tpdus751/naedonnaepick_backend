package com.naedonnaepick.backend.restaurant.dto;

import com.naedonnaepick.backend.restaurant.entity.RestaurantEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestaurantRecommendationDTO {
    private RestaurantEntity restaurant;
    private double distance;     // 계산된 거리
    private double probability;  // flask 응답으로 받은 확률
}