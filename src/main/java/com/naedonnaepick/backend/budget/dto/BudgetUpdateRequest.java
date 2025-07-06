package com.naedonnaepick.backend.budget.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BudgetUpdateRequest {
    private int budgetNo;
    private Integer totalBudget;
    private Integer remainingBudget;
    private String restaurantName; // "수정"
    private String menu;
    // "추가" 또는 "차감"
}