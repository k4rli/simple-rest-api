package com.karli.exceptions.missingParameter;

import com.karli.response.model.CustomResponse;
import com.karli.response.types.CustomResponseCodes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
class MissingParameterAdvice {
    @ResponseBody()
    @ExceptionHandler(MissingParameterException.class)
    ResponseEntity missingParameterHandler(MissingParameterException e) {
        CustomResponse customResponse = new CustomResponse(CustomResponseCodes.ERROR, e.getMessage());
        return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON_UTF8).body(customResponse);
    }
}
