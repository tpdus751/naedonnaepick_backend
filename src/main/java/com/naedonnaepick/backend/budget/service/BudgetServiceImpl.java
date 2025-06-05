package com.naedonnaepick.backend.budget.service;

import com.naedonnaepick.backend.budget.dao.BudgetDAO;
import com.naedonnaepick.backend.budget.entity.Budgets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetDAO budgetDAO;

    @Autowired
    public BudgetServiceImpl(BudgetDAO budgetDAO) {
        this.budgetDAO = budgetDAO;
    }

    @Override
    public Budgets setBudget(String email, Date startDate, Date endDate, int totalBudget) {
        return budgetDAO.setBudget(email, startDate, endDate, totalBudget);
    }

    @Override
    public List<Budgets> findAllBudgetsByEmail(String email) {
        return budgetDAO.findAllBudgetsByEmail(email);
    }

    @Override
    public Budgets findCurrentBudgetByEmailAndDate(String email, Date date) {
        return budgetDAO.findCurrentBudgetByEmailAndDate(email, date);
    }
}