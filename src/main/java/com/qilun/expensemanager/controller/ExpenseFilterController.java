package com.qilun.expensemanager.controller;

import com.qilun.expensemanager.dto.ExpenseDTO;
import com.qilun.expensemanager.dto.ExpenseFilterDTO;
import com.qilun.expensemanager.service.ExpenseService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.text.ParseException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ExpenseFilterController {

    private final ExpenseService expenseService;

    @GetMapping("/filterExpenses")
    public String filterExpenses(@ModelAttribute("filter")ExpenseFilterDTO expenseFilterDTO, Model model) throws ParseException {
        System.out.println("printing the filter dto: " +expenseFilterDTO);
        List<ExpenseDTO> list = expenseService.getFilteredExpenses(expenseFilterDTO);
        model.addAttribute("expenses", list);
        return "expenses-list";
    }
}
