package com.naedonnaepick.backend.budget.dao;

import com.naedonnaepick.backend.budget.entity.BudgetSpending;
import com.naedonnaepick.backend.budget.entity.Budgets;

import java.util.Date;
import java.util.List;

public interface BudgetSpendingDAO {
    List<BudgetSpending> findSpendingByBudgetNo(int budgetNo);

    BudgetSpending spendBudget(BudgetSpending budgetSpending);

}