package com.naedonnaepick.backend.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopVotedRestaurantDTO {
    private String name;
    private String address;
    private int positiveCount;
}