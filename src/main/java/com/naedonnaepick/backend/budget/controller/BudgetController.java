package com.naedonnaepick.backend.budget.controller;

import com.naedonnaepick.backend.budget.dto.BudgetSpendRequest;
import com.naedonnaepick.backend.budget.dto.BudgetUpdateRequest;
import com.naedonnaepick.backend.budget.dto.BudgetWithSpendingDTO;
import com.naedonnaepick.backend.budget.dto.EmailRequest;
import com.naedonnaepick.backend.budget.entity.BudgetSpending;
import com.naedonnaepick.backend.budget.entity.Budgets;
import com.naedonnaepick.backend.budget.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/budget")
@CrossOrigin(origins = "http://localhost:8081")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    // POST 요청으로 예산 설정
    @PostMapping("/set")
    public ResponseEntity<String> setBudget(@RequestBody Budgets budget) {

        System.out.println(budget);

        budgetService.setBudget(budget);

        return ResponseEntity.ok("good");
    }

    // 이메일로 예산 전체 조회
    @PostMapping("/all")
    public List<BudgetWithSpendingDTO> getAllBudgets(@RequestBody EmailRequest request) {
        String email = request.getEmail();

        List<Budgets> budgetList = budgetService.findAllBudgetsByEmail(email);
        List<BudgetWithSpendingDTO> result = new ArrayList<>();

        for (Budgets budget : budgetList) {
            // ❗ 예산 고유 번호(budget_no) 매핑 필요
            List<BudgetSpending> spendingList = budgetService.findSpendingByBudgetNo(budget.getBudgetNo());
            result.add(new BudgetWithSpendingDTO(budget, spendingList));
        }

        return result;
    }


    // 현재 날짜와 이메일로 특정 예산 조회
    @GetMapping("/current")
    public BudgetWithSpendingDTO getCurrentBudget(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "date") String date
    ) {
        Budgets budget = budgetService.findCurrentBudgetByEmailAndDate(email, java.sql.Date.valueOf(date));
        List<BudgetSpending> spendingList = budgetService.findSpendingByBudgetNo(budget.getBudgetNo());
        return new BudgetWithSpendingDTO(budget, spendingList);
    }


    // 먹기 버튼 누를 시 예산에서 금액 차감
    @PostMapping("/spend")
    public ResponseEntity<Object> spendBudget(@RequestBody BudgetSpendRequest request) {
        String email = request.getEmail();
        String date = request.getDate();
        int spend = request.getSpend();

        System.out.println(java.sql.Date.valueOf(date));

        Budgets budget = budgetService.findCurrentBudgetByEmailAndDate(email, java.sql.Date.valueOf(date));

        if (budget == null) {
            return new ResponseEntity<>("해당 날짜에 활성화된 예산이 없습니다.", HttpStatus.NOT_FOUND);
        }

        if (budget.getTotalBudget() < spend) {
            return new ResponseEntity<>("예산이 부족합니다.", HttpStatus.BAD_REQUEST);
        }

        // 소비 내역 객체 생성
        BudgetSpending budgetSpending = new BudgetSpending();
        budgetSpending.setBudgetNo(budget.getBudgetNo());
        budgetSpending.setRestaurantName(request.getRestaurant_name());
        budgetSpending.setDate(java.sql.Date.valueOf(date));
        budgetSpending.setMenu(request.getMenu());
        budgetSpending.setPrice(spend);
        budgetSpending.setRemainingAfter(budget.getTotalBudget() - spend);

        // 예산 차감
        budget.setTotalBudget(budget.getTotalBudget() - spend);

        // DB 저장
        Budgets updatedBudget = budgetService.updatedBudgetForSpending(budget);
        BudgetSpending budgetSpendingReturned = budgetService.spendBudget(budgetSpending);

        // 프론트에 필요한 응답 구성
        Map<String, Object> response = new HashMap<>();
        response.put("spending", budgetSpendingReturned); // 소비 내역 객체
        response.put("totalBudget", updatedBudget.getTotalBudget()); // 차감된 후 예산

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity<Object> updateBudget(@RequestBody BudgetUpdateRequest request) {
        System.out.println(request.toString());

        int budgetNo = request.getBudgetNo();
        Integer newBudget = request.getTotalBudget();
        String restaurantName = request.getRestaurantName();  // ex. "수정"
        String menu = request.getMenu();                      // ex. "추가" or "차감"
        Integer remainingBudget = request.getRemainingBudget();

        // 변동 기록이 있을 경우 → 소비 내역에 추가
        if (restaurantName != null && menu != null && !menu.isEmpty()) {
            BudgetSpending spending = new BudgetSpending();
            spending.setBudgetNo(budgetNo);
            spending.setRestaurantName(restaurantName);
            spending.setMenu(menu);
            if (menu.equals("추가")) {
                spending.setPrice(Math.abs(newBudget - remainingBudget)); // 변동 금액 기록
            } else {
                spending.setPrice(Math.abs(remainingBudget - newBudget)); // 변동 금액 기록
            }
            spending.setRemainingAfter(newBudget);
            spending.setDate(new java.sql.Date(System.currentTimeMillis()));

            budgetService.spendBudget(spending);
        }

        return new ResponseEntity<>("예산이 성공적으로 수정되었습니다.", HttpStatus.OK);
    }

}