package com.eatcommerce.eatcommerce.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eatcommerce.eatcommerce.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>{
    List<Expense> findAllByOrderByExpenseDateDesc();

    List<Expense> findByExpenseDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
