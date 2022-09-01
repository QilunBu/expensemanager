package com.qilun.expensemanager.service;

import com.qilun.expensemanager.dto.ExpenseDTO;
import com.qilun.expensemanager.entity.Expense;
import com.qilun.expensemanager.repository.ExpenseRepository;
import com.qilun.expensemanager.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;
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

    public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO) throws ParseException {
        //map the dto to entity
        Expense expense = mapToEntity(expenseDTO);
        //save entity  to database
        expense = expenseRepository.save(expense);
        //map the entity to dto
        return mapToDTO(expense);
    }

    private Expense mapToEntity(ExpenseDTO expenseDTO) throws ParseException {
        //map the dto to entity
        Expense expense = modelMapper.map(expenseDTO, Expense.class);
        //generate the expense id
        expense.setExpenseId(UUID.randomUUID().toString());
        //set the expense date
        expense.setDate(DateTimeUtil.convertStringToDate(expenseDTO.getDateString()));
        //return the expense entity
        return expense;
    }

}
