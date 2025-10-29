package ru.lazzzer64.bankrest.DTO;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CardRequestDTO {

    @NotBlank(message = "Номер карты обязателен")
    @Pattern(regexp = "\\d{16}", message = "Номер карты должен содержать 16 цифр")
    private String cardNumber;

    @NotBlank(message = "Владелец карты обязателен")
    @Size(min = 2, max = 100, message = "Имя владельца карты должно быть от 2 до 100 символов")
    private String cardHolder;

    @NotBlank(message = "Срок действия карты обязателен")
    @Pattern(regexp = "(0[1-9]|1[0-2])/[0-9]{2}", message = "Формат срока действия: ММ/ГГ")
    private String expiryDate;

    private BigDecimal initialBalance = BigDecimal.ZERO;

    public CardRequestDTO() {
    }

    public CardRequestDTO(String cardNumber, String cardHolder, String expiryDate) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.expiryDate = expiryDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
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
