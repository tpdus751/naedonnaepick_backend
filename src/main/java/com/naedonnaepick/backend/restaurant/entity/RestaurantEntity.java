package com.naedonnaepick.backend.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurants")
public class RestaurantEntity {
    /* 설명 필요 */
    @Id
    private int restaurantNo;

    private String name;

    private String address;

    private BigDecimal latitude; // float → BigDecimal 변경

    private BigDecimal longitude; // float → BigDecimal 변경

    private String category;

    private String status;

    private String phoneNumber;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "restaurantEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private RestaurantTags restaurantTags;

    @OneToMany(mappedBy = "restaurantEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RestaurantMenu> restaurantMenus;
}