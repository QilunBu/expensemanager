package com.qilun.expensemanager.controller;

import com.qilun.expensemanager.dto.UserDTO;
import com.qilun.expensemanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping({"/login", "/"})
    public  String showLoginPage(){
        return "login";
    }

    @GetMapping("/register")
    public  String showRegisterPage(Model model){
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, Model model){
        System.out.println("printing the user details: "+userDTO);
        if (result.hasErrors()){
            return "register";
        }
        userService.save(userDTO);
        model.addAttribute("successMsg", true);
        return "login";
    }

}
