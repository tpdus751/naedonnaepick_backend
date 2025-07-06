package com.naedonnaepick.backend.budget.dao;

import com.naedonnaepick.backend.budget.entity.BudgetSpending;
import com.naedonnaepick.backend.budget.entity.Budgets;

import java.sql.Date;
import java.util.List;

public interface BudgetDAO {
    Budgets setBudget(Budgets budget);

    List<Budgets> findAllBudgetsByEmail(String email);

    Budgets findCurrentBudgetByEmailAndDate(String email, Date date);

    List<BudgetSpending> findSpendingByBudgetNo(int budgetNo);

    Budgets updatedBudgetForSpending(Budgets budget);
}