package ru.lazzzer64.bankrest.DTO;

import ru.lazzzer64.bankrest.entity.Card;
import ru.lazzzer64.bankrest.entity.CardStatus;

import java.math.BigDecimal;

public class CardDTO {
    private Long id;
    private String maskedCardNumber;
    private String cardHolder;
    private String expiryDate;
    private CardStatus status;
    private BigDecimal balance;

    // Конструктор из Entity
    public CardDTO(Card card) {
        this.id = card.getId();
        this.maskedCardNumber = card.getMaskedCardNumber();
        this.cardHolder = card.getCardHolder();
        this.expiryDate = card.getExpiryDate();
        this.status = card.getStatus();
        this.balance = card.getBalance();
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaskedCardNumber() {
        return maskedCardNumber;
    }

    public void setMaskedCardNumber(String maskedCardNumber) {
        this.maskedCardNumber = maskedCardNumber;
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

    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}