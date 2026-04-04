package com.finance.dashboard.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/users/**").hasRole("ADMIN")
                .requestMatchers("/records").hasAnyRole("ADMIN", "ANALYST")
                .requestMatchers("/records/**").hasAnyRole("ADMIN", "ANALYST")
                .requestMatchers("/dashboard/**").hasAnyRole("ADMIN", "ANALYST", "VIEWER")
                .anyRequest().authenticated()
            );

        return http.build();
    }
}