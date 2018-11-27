package com.mountbirch.user.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class CustomerRegistrationFormDto {
    @Email(message = "Email is not valid")
    private String email;

    @Size(min = 8, message = "Password should be at least 8 characters long")
    private String password;
}
