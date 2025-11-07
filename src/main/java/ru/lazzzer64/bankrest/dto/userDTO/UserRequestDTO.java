package ru.lazzzer64.bankrest.dto.userDTO;


import jakarta.validation.constraints.NotBlank;

public class UserRequestDTO {
    @NotBlank(message = "Имя пользователя обязательно")
    private String username;

    @NotBlank(message = "Email обязателен")
    private String email;


    public UserRequestDTO() {
    }

    public UserRequestDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
