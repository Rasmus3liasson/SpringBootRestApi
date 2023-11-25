package com.example.springbootrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleBadRequest(IllegalArgumentException ex) {
        return buildProblemDetail(HttpStatus.BAD_REQUEST, "Not a valid request", ex.getLocalizedMessage() , ex.getMessage(),"https://example.com/documentation/errors/bad-request");
    }
    @ExceptionHandler(IllegalStateException.class)
    public ProblemDetail handleNotFound (IllegalStateException ex) {
        return buildProblemDetail(HttpStatus.NOT_FOUND, "Given id don't exist", ex.getLocalizedMessage(), ex.getMessage(),"https://example.com/documentation/errors/not-found");
    }
    @ExceptionHandler(RequestValidationException.class)
    public ProblemDetail handleRequestValidationException(RequestValidationException ex) {
        return buildProblemDetail(HttpStatus.BAD_REQUEST, "Request validation failed", ex.getLocalizedMessage(), ex.getMessage(), "https://example.com/documentation/errors/request-validation-failed");
    }

    private ProblemDetail buildProblemDetail(HttpStatus status, String title, String detail, String exMessage,String errorUrl) {
        ProblemDetail problemDetails = ProblemDetail
                .forStatusAndDetail(status, detail);
        problemDetails.setType(URI.create(errorUrl));
        problemDetails.setTitle(title);
        problemDetails.setDetail(detail);
        return problemDetails;
    }

}
