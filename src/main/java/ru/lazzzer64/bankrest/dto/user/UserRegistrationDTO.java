package ru.lazzzer64.bankrest.dto.user;

import ru.lazzzer64.bankrest.entity.BankAccount;

public class UserRegistrationDTO {
    private String username;
    private String password;
    private String email;

    // Конструкторы, геттеры, сеттеры
    public UserRegistrationDTO() {
    }

    public UserRegistrationDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Геттеры и сеттеры
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
