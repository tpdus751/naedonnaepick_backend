package com.naedonnaepick.backend.restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant_tags")
public class RestaurantTags {

    @Id
    private int restaurantNo;

    @OneToOne(fetch = FetchType.LAZY) // RestaurantEntity와의 관계 설정
    @JoinColumn(name = "restaurant_no", insertable = false, updatable = false) // FK 매핑
    private RestaurantEntity restaurantEntity;

    private int dfIdx;

    private float spicy, valueForMoney, kindness, cleanliness, atmosphere, largePortions, tasty, waiting, sweet, salty, savory, freshness, soloDining, trendy, parking;

}