package ru.lazzzer64.bankrest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lazzzer64.bankrest.DTO.CardRequestDTO;
import ru.lazzzer64.bankrest.DTO.CardResponseDTO;
import ru.lazzzer64.bankrest.entity.Card;
import ru.lazzzer64.bankrest.service.CardService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity<CardResponseDTO> createCard(@Valid CardRequestDTO requestDTO) {
        try {
            CardResponseDTO createdCard = cardService.createCard(requestDTO);
            return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public BigDecimal getAllCards() {
        Card cardsPage = cardService.getFirstCard();
        return cardsPage.getBalance();
    }
}
