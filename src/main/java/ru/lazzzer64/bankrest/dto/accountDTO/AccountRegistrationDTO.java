package ru.lazzzer64.bankrest.dto.accountDTO;

public class AccountRegistrationDTO {
    private Long userId;

    public AccountRegistrationDTO() {
    }

    public AccountRegistrationDTO(Long user_id) {
        this.userId = user_id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
