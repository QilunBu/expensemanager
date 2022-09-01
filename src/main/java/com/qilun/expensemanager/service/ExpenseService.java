package com.qilun.expensemanager.service;

import com.qilun.expensemanager.dto.ExpenseDTO;
import com.qilun.expensemanager.entity.Expense;
import com.qilun.expensemanager.repository.ExpenseRepository;
import com.qilun.expensemanager.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  //this annotation replace the autowired constructor, and add final to the repository
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;

//    @Autowired
//    public ExpenseService(ExpenseRepository expenseRepository) {
//        this.expenseRepository = expenseRepository;
//    }

    public List<ExpenseDTO> getAllExpenses(){
        List<Expense> list = expenseRepository.findAll();
        List<ExpenseDTO> expenseList = list.stream().map(this::mapToDTO).collect(Collectors.toList());
        return expenseList;
    }


    //This is a bad practice, we need to use model mapper to replace "//" code 
    private ExpenseDTO mapToDTO(Expense expense){
        ExpenseDTO expenseDTO = modelMapper.map(expense, ExpenseDTO.class);
        expenseDTO.setDateString(DateTimeUtil.convertDateToString(expenseDTO.getDate()));
        return expenseDTO;
//        ExpenseDTO expenseDTO = new ExpenseDTO();
//        expenseDTO.setId(expense.getId());
//        expenseDTO.setExpenseId(expense.getExpenseId());
//        expenseDTO.setAmount(expense.getAmount());
//        expenseDTO.setName(expense.getName());
//        expenseDTO.setDescription(expense.getDescription());
//        expenseDTO.setDate(expense.getDate());
//        return expenseDTO;
    }

}
