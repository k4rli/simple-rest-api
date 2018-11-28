package com.karli.customer.exceptions.customer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class CustomerNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    String customerNotFoundHandler(CustomerNotFoundException e) {
        return "{\"error\": \"" + e.getMessage() + "\"}";
    }
}
