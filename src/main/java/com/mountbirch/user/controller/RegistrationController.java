package com.mountbirch.user.controller;

import com.mountbirch.user.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("registration")
public class RegistrationController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/confirm/{registrationToken}")
    public String confirmRegistrationToken(@PathVariable String registrationToken){
        return "";
    }
}