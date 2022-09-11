package com.qilun.expensemanager.controller;

import com.qilun.expensemanager.dto.ExpenseDTO;
import com.qilun.expensemanager.dto.ExpenseFilterDTO;
import com.qilun.expensemanager.entity.Expense;
import com.qilun.expensemanager.service.ExpenseService;
import com.qilun.expensemanager.util.DateTimeUtil;
import com.qilun.expensemanager.validator.ExpenseValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("/expenses")
    public String showExpenseList(Model model){
        List<ExpenseDTO> list = expenseService.getAllExpenses();
        model.addAttribute("expenses", list);
        model.addAttribute("filter", new ExpenseFilterDTO(DateTimeUtil.getCurrentMonthStartDate(), DateTimeUtil.getCurrentMonthDate()));
        String totalExpenses = expenseService.totalExpenses(list);
        model.addAttribute("totalExpenses", totalExpenses);
        return "expenses-list";
    }

    @GetMapping("/createExpense")
    public String showExpenseForm(Model model){
        model.addAttribute("expense", new ExpenseDTO());
        return "expense-form";
    }

    @PostMapping("/saveOrUpdateExpense")
    public String saveOrUpdateExpenseDetails(@Valid @ModelAttribute("expense") ExpenseDTO expenseDTO, BindingResult result) throws ParseException {
        System.out.println("Printing the Expense DTO: " +expenseDTO);

        new ExpenseValidator().validate(expenseDTO, result);

        if (result.hasErrors()){
            return "expense-form";
        }
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
