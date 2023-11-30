package com.example.springbootrestapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomDeniedHandler {
    public AccessDeniedHandler handleAccessDenied() {
        return createErrorHandler("Access Denied", HttpStatus.FORBIDDEN);
    }

    public AccessDeniedHandler noDefinedRoute() {
        return createErrorHandler("This route don't exist", HttpStatus.NOT_FOUND);
    }

    public AccessDeniedHandler handleBadRequest() {
        return createErrorHandler("Bad Request", HttpStatus.BAD_REQUEST);
    }

    public AccessDeniedHandler handleUnauthorized() {
        return createErrorHandler("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    private AccessDeniedHandler createErrorHandler(String errorString, HttpStatus statusCode) {
        return (req, res, exception) -> {
            res.setStatus(statusCode.value());
            res.setContentType("application/json");

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> body = new HashMap<>();
            body.put("error", errorString);

            res.getWriter().write(objectMapper.writeValueAsString(body));
        };
    }

}
