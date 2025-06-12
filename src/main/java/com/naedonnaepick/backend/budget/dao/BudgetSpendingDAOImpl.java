package com.naedonnaepick.backend.budget.dao;

import com.naedonnaepick.backend.budget.entity.BudgetSpending;
import com.naedonnaepick.backend.budget.db.BudgetSpendingRepository;
import com.naedonnaepick.backend.budget.db.BudgetRepository;
import com.naedonnaepick.backend.budget.entity.Budgets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class BudgetSpendingDAOImpl implements BudgetSpendingDAO {

    private final BudgetSpendingRepository budgetSpendingRepository;

    public BudgetSpendingDAOImpl(BudgetSpendingRepository budgetSpendingRepository) {
        this.budgetSpendingRepository = budgetSpendingRepository;
    }

    @Override
    public List<BudgetSpending> findSpendingByBudgetNo(int budgetNo) {
        return budgetSpendingRepository.findByBudgetNo(budgetNo);
    }

    @Override
    public BudgetSpending spendBudget(BudgetSpending budgetSpending) {
        return budgetSpendingRepository.save(budgetSpending);
    }

}