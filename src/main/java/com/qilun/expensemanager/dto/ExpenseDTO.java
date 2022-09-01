package com.qilun.expensemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDTO {

    private Long id;

    private String expenseId;

    private String name;

    private String description;

    private BigDecimal amount;

    private Date date;

    private String dateString;
}
