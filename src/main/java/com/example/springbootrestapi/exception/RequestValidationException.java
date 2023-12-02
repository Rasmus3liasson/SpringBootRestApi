package com.example.springbootrestapi.exception;


public class RequestValidationException extends RuntimeException {
    public RequestValidationException(String message) {
        super(message);
    }
}