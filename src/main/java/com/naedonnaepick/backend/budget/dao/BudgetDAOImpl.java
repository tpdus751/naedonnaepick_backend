package com.naedonnaepick.backend.budget.dao;

import com.naedonnaepick.backend.budget.db.BudgetRepository;
import com.naedonnaepick.backend.budget.entity.BudgetSpending;
import com.naedonnaepick.backend.budget.entity.Budgets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
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
    public Budgets setBudget(Budgets budget) {
        return budgetRepository.save(budget);
    }

    @Override
    public List<Budgets> findAllBudgetsByEmail(String email) {
        return budgetRepository.findByEmail(email);
    }

    @Override
    public Budgets findCurrentBudgetByEmailAndDate(String email, Date date) {
        Optional<Budgets> budget = budgetRepository.findCurrentBudget(email, date);

        if (budget.isPresent()) {
            System.out.println("✅ 예산 있음: " + budget.get());
        } else {
            System.out.println("❌ 예산 없음");
        }

        return budget.orElse(null);
    }

    @Override
    public List<BudgetSpending> findSpendingByBudgetNo(int budgetNo) {
        return List.of();
    }

    @Override
    public Budgets updatedBudgetForSpending(Budgets budget) {
        return budgetRepository.save(budget);
    }


}