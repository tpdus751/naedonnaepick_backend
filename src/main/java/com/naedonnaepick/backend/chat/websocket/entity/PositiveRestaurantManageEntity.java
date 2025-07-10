package com.naedonnaepick.backend.chat.websocket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "positive_restaurant_manage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PositiveRestaurantManageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer positive_id;

    @Column(name = "sender_email")
    private String senderEmail;

    @Column(name = "room_no")
    private Integer roomNo;

    @Column(name = "restaurant_no")
    private Integer restaurantNo;

    private Integer positive_count = 1;

    @Column(name = "vote_month") // ✅ year_month → vote_month (충돌 방지)
    private LocalDate voteMonth;
}
