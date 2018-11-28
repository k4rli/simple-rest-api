package com.karli.customer.exceptions.missingParameter;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class MissingParameterAdvice {
    @ResponseBody
    @ExceptionHandler(MissingParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String missingParameterHandler(MissingParameterException e) {
        return "{\"error\": \"" + e.getMessage() + "\"}";
    }
}
