package com.qilun.expensemanager.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotBlank(message = "User name should not be null")
    private String name;

    @Email(message = "Please input valid email address")
    private String email;

    @NotBlank(message = "Password should not be null")
    @Size(min = 5, message = "Password should be minimum {min} characters")
    private String password;

    private String confirmpassword;
}
