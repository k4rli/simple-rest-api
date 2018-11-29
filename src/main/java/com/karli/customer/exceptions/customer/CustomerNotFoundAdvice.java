package com.karli.customer.exceptions.customer;

import com.karli.response.model.CustomResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
class CustomerNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(CustomerNotFoundException.class)
    ResponseEntity customerNotFoundHandler(CustomerNotFoundException e) {
        CustomResponse customResponse = new CustomResponse("error", e.getMessage());
        return ResponseEntity.status(204).contentType(MediaType.APPLICATION_JSON_UTF8).body(customResponse);
    }
}
