package ru.lazzzer64.bankrest.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;


@Entity
@Table(name = "bank_cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_number", nullable = false, unique = true, length = 16)
    private String cardNumber;

    @Column(name = "expiry_date", nullable = false)
    private String expiryDate;

    @Enumerated
    @Column(name = "status", nullable = false)
    private CardStatus status;

    @Column(name = "balance", precision = 15, scale = 2)
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cards")
    private BankAccount account;

    //Конструкторы
    public Card() {
        this.balance = BigDecimal.ZERO;
        this.status = CardStatus.ACTIVE;
    }

    public Card(String cardNumber, BankAccount bankAccount, String expiryDate) {
        this();
        this.cardNumber = cardNumber;
        this.account = bankAccount;
        this.expiryDate = expiryDate;
    }

    //Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BankAccount getAccount() {
        return account;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }

    //Бизнес-методы
    public String getMaskedCardNumber() {
        if (cardNumber == null || cardNumber.length() < 12) {
            return cardNumber;
        }
        return "**** **** **** " + cardNumber.substring(12);
    }

    public boolean isExpired() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            YearMonth expiry = YearMonth.parse(expiryDate, formatter);
            return YearMonth.now().isAfter(expiry);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardHolder='" + cardHolder + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", status=" + status +
                ", balance=" + balance +
                ", account=" + account +
                '}';
    }
}
