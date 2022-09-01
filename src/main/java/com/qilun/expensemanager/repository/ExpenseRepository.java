package com.qilun.expensemanager.repository;

import com.qilun.expensemanager.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
