package ru.lazzzer64.bankrest.security;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
