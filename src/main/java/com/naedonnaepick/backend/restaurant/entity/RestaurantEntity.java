// RestaurantEntity.java 파일
package com.naedonnaepick.backend.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty; // <-- 이 임포트 추가
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
    @Id
    private int restaurantNo;

    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String category;
    private String status;
    private String phoneNumber;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "restaurantEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private RestaurantTags restaurantTags;

    @OneToMany(mappedBy = "restaurantEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RestaurantMenu> restaurantMenus;

    @Transient
    private Double distance; // Double 타입 유지

    // 명시적으로 Getter와 Setter를 정의하고, @JsonProperty를 추가하여 JSON 직렬화 시 포함되도록 합니다.
    // Lombok의 @Data가 생성하는 getter/setter를 오버라이드합니다.
    @JsonProperty("distance") // JSON 응답에 "distance" 필드로 포함되도록 명시
    public Double getDistance() {
        return this.distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}