package ru.lazzzer64.bankrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lazzzer64.bankrest.entity.Card;
import ru.lazzzer64.bankrest.repository.CardRepository;

import java.util.List;

@RestController
public class CardController {
    private CardRepository cardRepository;

    @Autowired
    public CardController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @GetMapping("/cards/all")
    public List<Card> allCards() {
        return cardRepository.findAll();
    }
}
