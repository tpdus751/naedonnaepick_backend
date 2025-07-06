package com.naedonnaepick.backend.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_tags")
public class UserTagsEntity {

    @Id
    private String email; // users 테이블의 PK와 동일

    private int spicy;
    private int value_for_money;
    private int kindness;
    private int cleanliness;
    private int atmosphere;
    private int large_portions;
    private int tasty;
    private int waiting;
    private int sweet;
    private int salty;
    private int savory;
    private int freshness;
    private int solo_dining;
    private int trendy;
    private int parking;
}
