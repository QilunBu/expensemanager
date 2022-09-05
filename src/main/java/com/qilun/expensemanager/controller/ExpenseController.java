package com.qilun.expensemanager.controller;

import com.qilun.expensemanager.dto.ExpenseDTO;
import com.qilun.expensemanager.dto.ExpenseFilterDTO;
import com.qilun.expensemanager.entity.Expense;
import com.qilun.expensemanager.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;

@Controller
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("/expenses")
    public String showExpenseList(Model model){
        model.addAttribute("expenses", expenseService.getAllExpenses());
        model.addAttribute("filter", new ExpenseFilterDTO());
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

    @GetMapping("/deleteExpense")
    public String deleteExpense(@RequestParam String id){
        System.out.println("Print the expense delete id: " + id);
        expenseService.deleteExpense(id);
        return "redirect:/expenses";
    }

    @GetMapping("/updateExpense")
    public String updateExpense(@RequestParam String id, Model model){
        System.out.println("Print the expense update id: " + id);
        ExpenseDTO expense = expenseService.getExpenseById(id);
        model.addAttribute("expense", expense);
        return "expense-form";
    }

}
