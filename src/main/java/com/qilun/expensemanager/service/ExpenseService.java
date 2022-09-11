package com.qilun.expensemanager.service;

import com.ibm.icu.text.NumberFormat;
import com.qilun.expensemanager.dto.ExpenseDTO;
import com.qilun.expensemanager.dto.ExpenseFilterDTO;
import com.qilun.expensemanager.entity.Expense;
import com.qilun.expensemanager.entity.User;
import com.qilun.expensemanager.exception.ExpenseNotFoundException;
import com.qilun.expensemanager.repository.ExpenseRepository;
import com.qilun.expensemanager.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  //this annotation replace the autowired constructor, and add final to the repository
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;


    public List<ExpenseDTO> getAllExpenses(){
        User user = userService.getLoggedInUser();
        List<Expense> list = expenseRepository.findByDateBetweenAndUserId(
                java.sql.Date.valueOf(LocalDate.now().withDayOfMonth(1)),
                java.sql.Date.valueOf(LocalDate.now()),
                user.getId());
        List<ExpenseDTO> expenseList = list.stream().map(this::mapToDTO).collect(Collectors.toList());
        return expenseList;
    }


    //This is a bad practice, we need to use model mapper to replace "//" code 
    private ExpenseDTO mapToDTO(Expense expense){
        ExpenseDTO expenseDTO = modelMapper.map(expense, ExpenseDTO.class);
        expenseDTO.setDateString(DateTimeUtil.convertDateToString(expenseDTO.getDate()));
        return expenseDTO;
    }

    public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO) throws ParseException {
        //map the dto to entity
        Expense expense = mapToEntity(expenseDTO);
        //handle exception for future dates
        if (!expense.getDate().before(new java.util.Date())){
            throw new RuntimeException("Future date is not allowed");
        }
        //add the login user to the expense entity
        expense.setUser(userService.getLoggedInUser());
        //save entity  to database
        expense = expenseRepository.save(expense);
        //map the entity to dto
        return mapToDTO(expense);
    }

    private Expense mapToEntity(ExpenseDTO expenseDTO) throws ParseException {
        //map the dto to entity
        Expense expense = modelMapper.map(expenseDTO, Expense.class);
        //generate the expense id
        if (expense.getId() == null){
            expense.setExpenseId(UUID.randomUUID().toString());
        }
        //set the expense date
        expense.setDate(DateTimeUtil.convertStringToDate(expenseDTO.getDateString()));
        //return the expense entity
        return expense;
    }

    public void deleteExpense(String id){
        Expense existingExpense = getExpense(id);
        expenseRepository.delete(existingExpense);
    }

    public ExpenseDTO getExpenseById(String id){
        Expense existingExpense = getExpense(id);
        return mapToDTO(existingExpense);
    }

    private Expense getExpense(String id){
        return expenseRepository.findByExpenseId(id).orElseThrow(() -> new ExpenseNotFoundException("Expense not found for the id:"+id));
    }

    public List<ExpenseDTO> getFilteredExpenses(ExpenseFilterDTO expenseFilterDTO) throws ParseException {
        String keyword = expenseFilterDTO.getKeyword();
        String sortBy = expenseFilterDTO.getSortBy();
        String startDateString = expenseFilterDTO.getStartDate();
        String endDateString = expenseFilterDTO.getEndDate();

        //validation if the user does not input start date or end date
        Date startDate = !startDateString.isEmpty() ? DateTimeUtil.convertStringToDate(startDateString) : new Date(0);
        Date endDate = !endDateString.isEmpty() ? DateTimeUtil.convertStringToDate(endDateString) : new Date(System.currentTimeMillis());
        User user = userService.getLoggedInUser();
        List<Expense> list = expenseRepository.findByNameContainingAndDateBetweenAndUserId(keyword, startDate, endDate, user.getId());
        List<ExpenseDTO> filteredList = list.stream().map(this::mapToDTO).collect(Collectors.toList());
        if (sortBy.equals("date")){
            //sort by expense date
            filteredList.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        }else {
            //sort by expense amount
            filteredList.sort((o1, o2) -> o2.getAmount().compareTo(o1.getAmount()));
        }
        return filteredList;
    }

    public String totalExpenses(List<ExpenseDTO> expenses){
        BigDecimal sum = new BigDecimal(0);
        BigDecimal total = expenses.stream().map(x -> x.getAmount().add(sum)).reduce(BigDecimal.ZERO, BigDecimal::add);
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        return format.format(total).substring(2);
    }

}
