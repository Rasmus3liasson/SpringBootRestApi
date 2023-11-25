package com.example.springbootrestapi.exception;


public class RequestValidationException extends RuntimeException {
    public RequestValidationException(String message, RequestValidationException e) {
        super(message);
    }

    public RequestValidationException(String message, IllegalArgumentException e) {
    }
}