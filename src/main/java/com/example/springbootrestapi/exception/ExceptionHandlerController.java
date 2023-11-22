package com.example.springbootrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerController {
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpClientErrorException.BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(HttpClientErrorException.BadRequest ex) {
        return handleException(ex, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> handleIllegalStateException(IllegalStateException ex) {
        return handleException(ex, HttpStatus.NOT_FOUND);
    }
    private ResponseEntity<Map<String, String>> handleException(Exception ex, HttpStatus status) {
        Map<String, String> res = new HashMap<>();
        res.put("error", ex.getMessage());
        return ResponseEntity.status(status).body(res);
    }
}

