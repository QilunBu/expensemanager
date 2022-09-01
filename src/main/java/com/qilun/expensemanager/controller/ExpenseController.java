package com.qilun.expensemanager.controller;

import com.qilun.expensemanager.dto.ExpenseDTO;
import com.qilun.expensemanager.entity.Expense;
import com.qilun.expensemanager.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ExpenseController {

    //use the // code as sample data
//    private static List<ExpenseDTO> list = new ArrayList<>();

//    static {
//        ExpenseDTO expense = new ExpenseDTO();
//        expense.setName("Water bill");
//        expense.setDescription("Water bill");
//        expense.setAmount(new BigDecimal(700.00));
//        expense.setDate(new Date(System.currentTimeMillis()));
//        list.add(expense);
//
//        expense = new ExpenseDTO();
//        expense.setName("Gas bill");
//        expense.setDescription("Gas bill");
//        expense.setAmount(new BigDecimal(100.00));
//        expense.setDate(new Date(System.currentTimeMillis()));
//        list.add(expense);
//    }

    private final ExpenseService expenseService;

    @GetMapping("/expenses")
    public String showExpenseList(Model model){
        model.addAttribute("expenses", expenseService.getAllExpenses());
        return "expenses-list";
    }

    @GetMapping("/createExpense")
    public String showExpenseForm(Model model){
        model.addAttribute("expense", new ExpenseDTO());
        return "expense-form";
    }

    @PostMapping("/saveOrUpdateExpense")
    public String saveOrUpdateExpenseDetails(@ModelAttribute("expense") ExpenseDTO expenseDTO) throws ParseException {
        System.out.println("Printing the Expense DTO: " +expenseDTO);
        expenseService.saveExpenseDetails(expenseDTO);
        return "redirect:/expenses";
    }


}
