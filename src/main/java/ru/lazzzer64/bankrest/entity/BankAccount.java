package ru.lazzzer64.bankrest.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bank_accounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Card> cards = new ArrayList<>();

//    @OneToOne()
//    @JoinColumn(name = "id")
//    private User user;

    //Геттеры и сеттеры


    public BankAccount() {
    }

//    public BankAccount(String accountHolder, List<Card> cards, User user) {
//        this.cards = cards;
//        this.user = user;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public List<Card> getCards() {
//        return cards;
//    }
//
//    public void setCards(List<Card> cards) {
//        this.cards = cards;
//    }



    //Бизнес-логика
//    public void addCard(Card card) {
//        cards.add(card);
//        card.setAccount(this);
//    }
//
//    public void removeCard(Card card) {
//        cards.remove(card);
//        card.setAccount(null);
//    }


}
