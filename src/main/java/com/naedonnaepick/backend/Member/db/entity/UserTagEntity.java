// src/main/java/com/example/team_project_2_1/Member/db/entity/UserTagEntity.java
package com.naedonnaepick.backend.Member.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_tags")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTagEntity {

    @Id
    private String email;  // 사용자 식별용

    @Column(name = "value_for_money")
    private Integer valueForMoney;       // 가성비

    @Column(name = "large_portions")
    private Integer largePortions;       // 양 많음

    private Integer kindness;            // 친절함
    private Integer cleanliness;         // 청결함
    private Integer atmosphere;          // 분위기

    @Column(name = "solo_dining")
    private Integer soloDining;          // 혼밥 가능

    private Integer tasty;               // 맛집
    private Integer trendy;              // 트렌디함

    private Integer parking;             // 주차 편의성
    private Integer waiting;             // 웨이팅 있음
    private Integer spicy;               // 매운맛
    private Integer sweet;               // 달콤함
    private Integer salty;               // 짭짤함
    private Integer savory;              // 고소함
    private Integer freshness;           // 신선함
}
