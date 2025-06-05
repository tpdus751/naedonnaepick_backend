package com.naedonnaepick.backend.budget.controller;

import com.naedonnaepick.backend.budget.entity.Budgets;
import com.naedonnaepick.backend.budget.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budget")
@CrossOrigin(origins = "*")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    // POST 요청으로 예산 설정
    @PostMapping("/set")
    public Budgets setBudget(@RequestBody Budgets budget) {
        return budgetService.setBudget(
                budget.getEmail(),
                budget.getStartDate(),
                budget.getEndDate(),
                budget.getTotalBudget()
        );
    }

    // 이메일로 예산 전체 조회
    @GetMapping("/all")
    public List<Budgets> getAllBudgets(@RequestParam(name = "email") String email) {
        return budgetService.findAllBudgetsByEmail(email);
    }

    // 현재 날짜와 이메일로 특정 예산 조회
    @GetMapping("/current")
    public Budgets getCurrentBudget(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "date") String date
    ) {
        return budgetService.findCurrentBudgetByEmailAndDate(email, java.sql.Date.valueOf(date));
    }
}