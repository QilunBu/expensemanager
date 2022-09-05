package com.qilun.expensemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseFilterDTO {

    private String keyword;

    private String sortBy;

    private String startDate;

    private String endDate;
}
