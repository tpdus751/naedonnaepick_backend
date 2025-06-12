package com.naedonnaepick.backend.budget.db;

import com.naedonnaepick.backend.budget.entity.BudgetSpending;
import com.naedonnaepick.backend.budget.entity.Budgets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetSpendingRepository extends JpaRepository<BudgetSpending, Integer> {
    List<BudgetSpending> findByBudgetNo(int budgetNo);
}