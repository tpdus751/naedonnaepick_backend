package com.naedonnaepick.backend.budget.service;

import com.naedonnaepick.backend.budget.entity.Budgets;

import java.util.Date;
import java.util.List;

public interface BudgetService {
    Budgets setBudget(String email, Date startDate, Date endDate, int totalBudget);

    List<Budgets> findAllBudgetsByEmail(String email);

    Budgets findCurrentBudgetByEmailAndDate(String email, Date date);

    Budgets spendBudget(String email, Date date, int spend);
}