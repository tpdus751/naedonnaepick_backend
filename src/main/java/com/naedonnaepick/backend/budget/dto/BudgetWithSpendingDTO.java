package com.naedonnaepick.backend.budget.dto;

import com.naedonnaepick.backend.budget.entity.Budgets;
import com.naedonnaepick.backend.budget.entity.BudgetSpending;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BudgetWithSpendingDTO {
    private Budgets budget;
    private List<BudgetSpending> spendingList;
}
