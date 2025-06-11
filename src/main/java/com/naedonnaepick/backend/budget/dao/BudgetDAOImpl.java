package com.naedonnaepick.backend.budget.dao;

import com.naedonnaepick.backend.budget.db.BudgetRepository;
import com.naedonnaepick.backend.budget.entity.Budgets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class BudgetDAOImpl implements BudgetDAO {

    private final BudgetRepository budgetRepository;

    @Autowired
    public BudgetDAOImpl(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public Budgets setBudget(String email, Date startDate, Date endDate, int totalBudget) {
        Budgets budget = new Budgets();
        budget.setEmail(email);
        budget.setStartDate(startDate);
        budget.setEndDate(endDate);
        budget.setTotalBudget(totalBudget);
        return budgetRepository.save(budget);
    }

    @Override
    public List<Budgets> findAllBudgetsByEmail(String email) {
        return budgetRepository.findByEmail(email);
    }

    @Override
    public Budgets findCurrentBudgetByEmailAndDate(String email, Date date) {
        Optional<Budgets> budget = budgetRepository.findCurrentBudget(email, date);
        return budget.orElse(null);
    }

    @Override
    public Budgets spendBudget(String email, Date date, int spend) {
        // Get the current budget
        Budgets currentBudget = findCurrentBudgetByEmailAndDate(email, date);
        if (currentBudget != null) {
            currentBudget.setTotalBudget(currentBudget.getTotalBudget() - spend); // Adjust budget amount
            return budgetRepository.save(currentBudget); // Save updated budget
        }
        return null;
    }
}