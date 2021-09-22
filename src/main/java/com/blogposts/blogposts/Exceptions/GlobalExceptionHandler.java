package com.blogposts.blogposts.Exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    private ResponseEntity<Object> handleApiException(ApiException ex) {
        Map<String, String> errorObject = new HashMap<>();
        errorObject.put("error", ex.getLocalizedMessage());
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, errorObject);

        return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
