package ru.lazzzer64.bankrest.dto.accountDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ru.lazzzer64.bankrest.entity.CardStatus;
import ru.lazzzer64.bankrest.entity.User;

public class CardRequestDTO {
    @NotBlank(message = "Номер карты обязателен")
    @Pattern(regexp = "\\d{16}", message = "Номер карты должен содержать 16 цифр")
    private String cardNumber;

    @NotBlank(message = "Владелец карты обязателен")
    @Size(min = 2, max = 100, message = "Имя владельца карты должно быть от 2 до 100 символов")
    private User owner;

    @NotBlank(message = "Срок действия карты обязателен")
    @Pattern(regexp = "(0[1-9]|1[0-2])/[0-9]{2}", message = "Формат срока действия: ММ/ГГ")
    private String expiryDate;

    private CardStatus status = CardStatus.ACTIVE;

    public CardRequestDTO() {
    }

    public CardRequestDTO(String cardNumber, String expiryDate,
                          User owner, CardStatus status) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.owner = owner;
        this.status = status;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }
}
