package com.blogposts.blogposts.Exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

public class ErrorDetails {
    private Map<String, String> message = new HashMap<>();
    private HttpStatus httpStatus;

    public ErrorDetails(HttpStatus httpStatus, Map<String, String> message) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public Map<String, String> getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setMessage(Map<String, String> message) {
        this.message = message;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
