package ru.lazzzer64.bankrest.dto;

import jakarta.validation.constraints.*;
import ru.lazzzer64.bankrest.entity.User;

import java.math.BigDecimal;

public class CardRequestDTO {

    @NotBlank(message = "Номер карты обязателен")
    @Pattern(regexp = "\\d{16}", message = "Номер карты должен содержать 16 цифр")
    private String cardNumber;

    @NotBlank(message = "Владелец карты обязателен")
    @Size(min = 2, max = 100, message = "Имя владельца карты должно быть от 2 до 100 символов")
    private User user;

    @NotBlank(message = "Срок действия карты обязателен")
    @Pattern(regexp = "(0[1-9]|1[0-2])/[0-9]{2}", message = "Формат срока действия: ММ/ГГ")
    private String expiryDate;

    private BigDecimal initialBalance = BigDecimal.ZERO;

    public CardRequestDTO() {
    }

    public CardRequestDTO(String cardNumber, User user, String expiryDate) {
        this.cardNumber = cardNumber;
        this.user = user;
        this.expiryDate = expiryDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }
}
