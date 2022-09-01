package com.qilun.expensemanager;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExpenseManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpenseManagerApplication.class, args);
    }

    @Bean   //we want to use model mapper to map the expense to dto, we have to make the bean of model mapper here.
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
