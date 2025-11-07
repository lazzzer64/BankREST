package ru.lazzzer64.bankrest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().permitAll()  // Разрешаем все запросы
                )
                .csrf(csrf -> csrf.disable())  // Отключаем CSRF
                .headers(headers -> headers.disable()); // Отключаем security headers

        return http.build();
    }
}
