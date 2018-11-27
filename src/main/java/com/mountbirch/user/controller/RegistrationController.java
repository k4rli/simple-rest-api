package com.mountbirch.user.controller;

import com.mountbirch.commons.service.DocumentGenerationService;
import com.mountbirch.messaging.service.EmailService;
import com.mountbirch.user.model.User;
import com.mountbirch.user.model.UserRegistrationFormDto;
import com.mountbirch.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Created by Kaur Laanemäe on 21/11/2018.
 * Mountbirch OÜ
 * kaur@mountbirch.com
 */
@Slf4j
@RestController
@RequestMapping("registration")
public class RegistrationController {

    @Autowired
    UserService userService;

    @Autowired
    DocumentGenerationService documentGenerationService;

    @Autowired
    EmailService emailService;

    @PostMapping
    public ResponseEntity registerNewUser(@RequestBody UserRegistrationFormDto userRegistrationForm) throws MessagingException {
        if(userService.userExists(userRegistrationForm.getEmail())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<UserRegistrationFormDto>> violations = validator.validate(userRegistrationForm);
        if(!violations.isEmpty()){
            for (ConstraintViolation<UserRegistrationFormDto> violation : violations) {
                log.error(violation.getMessage());
            }
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User user = userService.createUserFromRegistrationForm(userRegistrationForm);

        String email = documentGenerationService.createHtmlFromTemplate("email/userRegistration", null);
        emailService.sendEmailMessage(user.getPrimaryEmail(), "User registration!", email);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/confirm/{registrationToken}")
    public String confirmRegistrationToken(@PathVariable String registrationToken){
        return "";
    }
}