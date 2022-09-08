package com.qilun.expensemanager.repository;

import com.qilun.expensemanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
