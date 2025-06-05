package com.naedonnaepick.backend.budget.db;

import com.naedonnaepick.backend.budget.entity.Budgets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budgets, Integer> {

    List<Budgets> findByEmail(String email);

    @Query("SELECT b FROM Budgets b WHERE b.email = :email AND :date BETWEEN b.startDate AND b.endDate")
    Optional<Budgets> findCurrentBudget(String email, Date date);
}