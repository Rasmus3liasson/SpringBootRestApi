package com.example.springbootrestapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableRetry
public class SpringSecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("AdminPassword123"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler handleAccessDenied() {
        return createErrorHandler("Access Denied", HttpStatus.FORBIDDEN);
    }

    @Bean
    public AccessDeniedHandler noDefinedRoute() {
        return createErrorHandler("This route don't exist", HttpStatus.NOT_FOUND);
    }

    @Bean
    public AccessDeniedHandler handleBadRequest() {
        return createErrorHandler("Bad Request", HttpStatus.BAD_REQUEST);
    }

    public AccessDeniedHandler createErrorHandler(String errorString, HttpStatus statusCode) {
        return (req, res, exception) -> {
            res.setStatus(statusCode.value());
            res.setContentType("application/json");

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> body = new HashMap<>();
            body.put("error", errorString);

            res.getWriter().write(objectMapper.writeValueAsString(body));

        };
    }


    /*Might remove csrf disable later on*/
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(HttpMethod.GET, "/api/categories", "/api/categories/*").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/categories").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.GET, "/api/location","/api/location/*", "/api/location/category/*").permitAll()

                                .requestMatchers(HttpMethod.GET, "/api/location/userId").authenticated()

                                .requestMatchers(HttpMethod.GET, "/api/location/area","/api/location/area/**").permitAll()

                                .requestMatchers(HttpMethod.PUT, "/api/location/update/*").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/location/delete/*").authenticated()
                                .requestMatchers(HttpMethod.POST, "/api/location").authenticated()


                                .anyRequest().denyAll()
                )
                .csrf().disable()
                .exceptionHandling()
                .defaultAccessDeniedHandlerFor(noDefinedRoute(), (RequestMatcher) null)
                .accessDeniedHandler(handleAccessDenied())
                .accessDeniedHandler(handleBadRequest());
        return http.build();
    }
}


