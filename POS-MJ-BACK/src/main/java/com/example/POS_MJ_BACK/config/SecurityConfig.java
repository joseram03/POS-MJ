package com.example.POS_MJ_BACK.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Desactiva CSRF para APIs REST
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()  // Permite todo sin autenticación
            );
        
        return http.build();
    }
}
