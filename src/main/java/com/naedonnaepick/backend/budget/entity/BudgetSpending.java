package com.naedonnaepick.backend.budget.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "budget_spending")
public class BudgetSpending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int spendingNo;

    private int budgetNo;

    private String restaurantName;

    private Date date;

    private String menu;

    private int price;

    private int remainingAfter;

}