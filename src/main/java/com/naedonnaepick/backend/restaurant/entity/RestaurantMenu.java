package com.naedonnaepick.backend.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "restaurant_menus")
public class RestaurantMenu {

    @Id
    private int menuNo;

    @ManyToOne(fetch = FetchType.LAZY) // RestaurantEntity와 관계 설정
    @JoinColumn(name = "restaurant_no") // FK 매핑
    @JsonIgnore // JSON 출력 시 무시
    private RestaurantEntity restaurantEntity;

    private String menu;

    private String description;

    private int price;

}