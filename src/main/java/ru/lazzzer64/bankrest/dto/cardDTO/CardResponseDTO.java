package ru.lazzzer64.bankrest.dto.cardDTO;

import ru.lazzzer64.bankrest.entity.CardStatus;
import ru.lazzzer64.bankrest.entity.User;

import java.math.BigDecimal;

public class CardResponseDTO {
    private Long id;
    private User owner;
    private String cardNumber;
    private String expiryDate;
    private CardStatus cardStatus;
    private BigDecimal balance;

    public CardResponseDTO() {
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
