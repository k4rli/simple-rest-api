package com.karli.customer.exceptions.customer;

import com.karli.response.model.CustomResponse;
import com.karli.response.types.CustomResponseCodes;
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
        CustomResponse customResponse = new CustomResponse(CustomResponseCodes.ERROR, e.getMessage());
        return ResponseEntity.status(204).contentType(MediaType.APPLICATION_JSON_UTF8).body(customResponse);
    }
}
