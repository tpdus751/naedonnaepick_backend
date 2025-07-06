package com.naedonnaepick.backend.restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurant_tags")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTags {

    @Id
    @Column(name = "restaurant_no")
    private int restaurantNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_no", insertable = false, updatable = false)
    private RestaurantEntity restaurantEntity;

    @Column(name = "df_idx")
    private int dfIdx;

    @Column(name = "spicy")
    private float spicy;

    @Column(name = "kindness")
    private float kindness;

    @Column(name = "cleanliness")
    private float cleanliness;

    @Column(name = "atmosphere")
    private float atmosphere;

    @Column(name = "tasty")
    private float tasty;

    @Column(name = "waiting")
    private float waiting;

    @Column(name = "sweet")
    private float sweet;

    @Column(name = "salty")
    private float salty;

    @Column(name = "savory")
    private float savory;

    @Column(name = "freshness")
    private float freshness;

    @Column(name = "trendy")
    private float trendy;

    @Column(name = "parking")
    private float parking;

    @Column(name = "value_for_money")
    private float valueForMoney;

    @Column(name = "large_portions")
    private float largePortions;

    @Column(name = "solo_dining")
    private float soloDining;

}