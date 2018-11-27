package com.mountbirch.user.controller;

import com.mountbirch.user.model.UserRegistrationFormDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

/**
 * Created by Kaur Laanemäe on 21/11/2018.
 * Mountbirch OÜ
 * kaur@mountbirch.com
 */
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
