package ru.lazzzer64.bankrest.dto.userDTO;

public class UsernameResponseDTO {
    private String username;

    public UsernameResponseDTO() {
    }

    public UsernameResponseDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
