package com.example.springbootrestapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;


@Configuration
@EnableWebSecurity
@EnableRetry
public class SpringSecurityConfig {


    private final CustomDeniedHandler customDeniedHandler;

    public SpringSecurityConfig(CustomDeniedHandler customDeniedHandler) {
        this.customDeniedHandler = customDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    /*Might remove csrf disable later on*/
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(HttpMethod.GET, "/api/categories", "/api/categories/*").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/categories").hasAuthority("SCOPE_admin")

                                .requestMatchers(HttpMethod.GET, "/api/location", "/api/location/*", "/api/location/category/*").permitAll()

                                .requestMatchers(HttpMethod.GET, "/api/location/userId").authenticated()

                                .requestMatchers(HttpMethod.GET, "/api/location/area", "/api/location/area/**").permitAll()

                                .requestMatchers(HttpMethod.PUT, "/api/location/update/*").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/location/delete/*").authenticated()
                                .requestMatchers(HttpMethod.POST, "/api/location").authenticated()


                                .anyRequest().denyAll()
                )
                .csrf().disable()
                .exceptionHandling()
                .defaultAccessDeniedHandlerFor(customDeniedHandler.noDefinedRoute(), (RequestMatcher) null)
                .accessDeniedHandler(customDeniedHandler.handleBadRequest())
                .accessDeniedHandler(customDeniedHandler.handleAccessDenied())
                .accessDeniedHandler(customDeniedHandler.handleUnauthorized());

        return http.build();
    }
}


