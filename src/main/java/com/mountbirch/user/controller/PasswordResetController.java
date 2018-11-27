package com.mountbirch.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("password/reset")
public class PasswordResetController {

    @PostMapping
    public ResponseEntity requestPasswordResetToken(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/confirm/{registrationToken}")
    public String confirmRegistrationToken(@PathVariable String registrationToken){
        return "";
    }
}
