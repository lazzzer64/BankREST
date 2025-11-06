package ru.lazzzer64.bankrest.dto.userDTO;

public class UserUpdateDTO {
    private String username;
    private String email;

    // Конструкторы, геттеры, сеттеры

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}