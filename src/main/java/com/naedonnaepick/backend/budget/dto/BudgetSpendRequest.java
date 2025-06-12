package com.naedonnaepick.backend.budget.dto;

import lombok.Data;

@Data
public class BudgetSpendRequest {
    private String email;
    private String date;  // yyyy-MM-dd 형식
    private int spend;
    private String restaurant_name;
    private String menu;
    private Integer remainingAfter;  // optional
}
