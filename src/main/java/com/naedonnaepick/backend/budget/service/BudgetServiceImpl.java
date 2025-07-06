package com.naedonnaepick.backend.budget.service;

import com.naedonnaepick.backend.budget.dao.BudgetDAO;
import com.naedonnaepick.backend.budget.dao.BudgetSpendingDAO;
import com.naedonnaepick.backend.budget.entity.BudgetSpending;
import com.naedonnaepick.backend.budget.entity.Budgets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetDAO budgetDAO;
    private final BudgetSpendingDAO budgetSpendingDAO;

    @Autowired
    public BudgetServiceImpl(BudgetDAO budgetDAO, BudgetSpendingDAO budgetSpendingDAO) {

        this.budgetDAO = budgetDAO;
        this.budgetSpendingDAO = budgetSpendingDAO;
    }

    @Override
    public Budgets setBudget(Budgets budget) {
        return budgetDAO.setBudget(budget);
    }

    @Override
    public List<Budgets> findAllBudgetsByEmail(String email) {
        return budgetDAO.findAllBudgetsByEmail(email);
    }

    @Override
    public Budgets findCurrentBudgetByEmailAndDate(String email, Date date) {
        return budgetDAO.findCurrentBudgetByEmailAndDate(email, date);
    }

    @Override
    public BudgetSpending spendBudget(BudgetSpending budgetSpending) {
        return budgetSpendingDAO.spendBudget(budgetSpending);
    }

    public List<BudgetSpending> findSpendingByBudgetNo(int budgetNo) {
        return budgetSpendingDAO.findSpendingByBudgetNo(budgetNo);
    }

    @Override
    public Budgets updatedBudgetForSpending(Budgets budget) {
        return budgetDAO.updatedBudgetForSpending(budget);
    }
}