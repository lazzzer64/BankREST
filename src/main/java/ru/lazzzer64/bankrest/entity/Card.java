package ru.lazzzer64.bankrest.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User owner;

    @Column(nullable = false, unique = true, length = 16)
    private String cardNumber;

    @Column(nullable = false)
    private String expiryDate;

    @Enumerated
    @Column(nullable = false)
    private CardStatus status;

    @Column(precision = 15, scale = 2)
    private BigDecimal balance;


    public Card() {
        this.balance = BigDecimal.ZERO;
        this.status = CardStatus.ACTIVE;
    }

    public Card(BigDecimal balance, String cardNumber, String expiryDate, Long id, User owner, CardStatus status) {
        this.balance = balance;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.id = id;
        this.owner = owner;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getMaskedCardNumber() {
        if (cardNumber == null || cardNumber.length() < 12) {
            return cardNumber;
        }
        return "**** **** **** " + cardNumber.substring(12);
    }
}
