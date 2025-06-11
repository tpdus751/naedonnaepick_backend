package com.naedonnaepick.backend.budget.controller;

import com.naedonnaepick.backend.budget.entity.Budgets;
import com.naedonnaepick.backend.budget.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // 먹기 버튼 누를 시 예산에서 금액 차감
    @GetMapping("/spend")
    public ResponseEntity<Object> spendBudget(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "date") String date,
            @RequestParam(name = "spend") int spend
    ) {
        Budgets budget = budgetService.findCurrentBudgetByEmailAndDate(email, java.sql.Date.valueOf(date));

        if (budget == null) {
            return new ResponseEntity<>("해당 날짜에 활성화된 예산이 없습니다.", HttpStatus.NOT_FOUND);
        }

        if (budget.getTotalBudget() < spend) {
            return new ResponseEntity<>("예산이 부족합니다.", HttpStatus.BAD_REQUEST);
        }

        Budgets updatedBudget = budgetService.spendBudget(email, java.sql.Date.valueOf(date), spend);
        return new ResponseEntity<>(updatedBudget, HttpStatus.OK);
    }
}