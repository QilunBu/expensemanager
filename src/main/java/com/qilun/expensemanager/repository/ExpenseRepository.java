package com.qilun.expensemanager.repository;

import com.qilun.expensemanager.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findByExpenseId(String id);

    List<Expense> findByNameContainingAndDateBetweenAndUserId(String keyword, Date startDate, Date endDate, Long id);

    List<Expense> findByUserId(Long id);

    List<Expense> findByDateBetweenAndUserId(Date startDate, Date endDate, Long id);

}
