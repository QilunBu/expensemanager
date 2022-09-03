package com.qilun.expensemanager.repository;

import com.qilun.expensemanager.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findByExpenseId(String id);

}
