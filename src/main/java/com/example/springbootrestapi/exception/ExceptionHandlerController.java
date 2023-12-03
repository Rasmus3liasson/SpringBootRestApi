package com.example.springbootrestapi.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleBadRequest(IllegalArgumentException ex) {
        return buildProblemDetail(HttpStatus.BAD_REQUEST, "Not a valid request", ex.getLocalizedMessage() , ex.getMessage(),"https://example.com/documentation/errors/bad-request");
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleNotFound (EntityNotFoundException ex) {
        return buildProblemDetail(HttpStatus.NOT_FOUND, "Couldn't' find object", ex.getLocalizedMessage(), ex.getMessage(),"https://example.com/documentation/errors/not-found");
    }
    @ExceptionHandler(RequestValidationException.class)
    public ProblemDetail handleRequestValidationException(RequestValidationException ex) {
        return buildProblemDetail(HttpStatus.BAD_REQUEST, "Request validation failed", ex.getLocalizedMessage(), ex.getMessage(), "https://example.com/documentation/errors/request-validation-failed");
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleRequestValidationException(AccessDeniedException ex) {
        return buildProblemDetail(HttpStatus.UNAUTHORIZED, "Not Authorized", ex.getLocalizedMessage(), ex.getMessage(), "https://example.com/documentation/errors/unauthorized");
    }
    @ExceptionHandler(ConflictException.class)
    public ProblemDetail handleConflictException(ConflictException ex) {
        return buildProblemDetail(HttpStatus.CONFLICT, "Conflict", ex.getLocalizedMessage(), ex.getMessage(), "https://example.com/documentation/errors/conflict");

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
        return buildProblemDetail(HttpStatus.BAD_REQUEST, "Validation failed", ex.getLocalizedMessage(), ex.getMessage(), "https://example.com/documentation/errors/validation-failed");
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
