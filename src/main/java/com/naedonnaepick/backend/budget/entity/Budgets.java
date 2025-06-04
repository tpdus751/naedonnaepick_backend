package com.naedonnaepick.backend.budget.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "budgets")
public class Budgets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int budgetNo;

    private String email;

    private Date startDate;

    private Date endDate;

    private int totalBudget;

}