package ru.lazzzer64.bankrest.security;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
