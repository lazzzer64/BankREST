package ru.lazzzer64.bankrest.dto.accountDTO;

import jakarta.validation.constraints.NotBlank;

public class AccountRequestDTO {
    @NotBlank(message = "ID владельца не может быть пустым")
    private Long userId;

    public AccountRequestDTO() {
    }

    public AccountRequestDTO(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
