package com.eatcommerce.eatcommerce.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.eatcommerce.eatcommerce.DTO.ExpenseDTO;
import com.eatcommerce.eatcommerce.service.AuditService;
import com.eatcommerce.eatcommerce.service.ExpensesService;

@RestController
@RequestMapping("/expenses")
public class ExpensesController {

    @Autowired
    private ExpensesService expensesService;

    @Autowired
    private AuditService auditService;

    @PostMapping("/create-expense")
    public ResponseEntity<ExpenseDTO> createExpense(
            @RequestBody ExpenseDTO request,
            Authentication auth) {
        ExpenseDTO created = expensesService.createExpense(request);
        auditService.log(auth.getName(), "CREATE_EXPENSE", "Expense",
                String.valueOf(created.getExpenseId()),
                "Creó gasto: " + created.getConcept() + " por $" + created.getTotalPrice());
        return ResponseEntity.ok(created);
    }

    @PutMapping("/edit-expense")
    public ResponseEntity<ExpenseDTO> editExpense(
            @RequestParam Long expenseId,
            @RequestBody ExpenseDTO request,
            Authentication auth) {
        ExpenseDTO updated = expensesService.editExpense(expenseId, request);
        auditService.log(auth.getName(), "UPDATE_EXPENSE", "Expense",
                String.valueOf(expenseId),
                "Editó gasto: " + updated.getConcept() + " por $" + updated.getTotalPrice());
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/get-all-expenses")
    public ResponseEntity<List<ExpenseDTO>> getAllExpenses() {
        return ResponseEntity.ok(expensesService.getAllExpenses());
    }

    @GetMapping("/get-expense-by-id")
    public ResponseEntity<ExpenseDTO> getExpenseById(@RequestParam Long expenseId) {
        return ResponseEntity.ok(expensesService.getExpenseById(expenseId));
    }

    @DeleteMapping("/delete-expense")
    public ResponseEntity<String> deleteExpense(
            @RequestParam Long expenseId,
            Authentication auth) {
        auditService.log(auth.getName(), "DELETE_EXPENSE", "Expense",
                String.valueOf(expenseId),
                "Eliminó gasto ID: " + expenseId);
        expensesService.deleteExpense(expenseId);
        return ResponseEntity.ok("Expense deleted successfully");
    }

    @GetMapping("/get-expenses-by-day")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByDay(@RequestParam LocalDate date) {
        return ResponseEntity.ok(expensesService.getExpensesByDay(date));
    }

    @GetMapping("/get-expenses-by-date-range")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(expensesService.getExpensesByDateRange(startDate, endDate));
    }
}