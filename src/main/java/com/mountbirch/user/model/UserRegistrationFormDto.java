package com.mountbirch.user.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * Created by Kaur Laanemäe on 21/11/2018.
 * Mountbirch OÜ
 * kaur@mountbirch.com
 */
@Data
public class UserRegistrationFormDto {
    @Email(message = "Email is not valid")
    private String email;

    @Size(min = 8, message = "Password should be at least 8 characters long")
    private String password;
}
