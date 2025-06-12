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
    @Column(name = "spending_no")
    private int spendingNo;

    @Column(name = "budget_no")
    private int budgetNo;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "date")
    private Date date;

    @Column(name = "menu")
    private String menu;

    @Column(name = "price")
    private int price;

    @Column(name = "remaining_after")
    private int remainingAfter;

}