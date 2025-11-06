package ru.lazzzer64.bankrest.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lazzzer64.bankrest.dto.CardRequestDTO;
import ru.lazzzer64.bankrest.dto.CardResponseDTO;
import ru.lazzzer64.bankrest.entity.Card;
import ru.lazzzer64.bankrest.service.CardService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    //TODO: Реализовать метод создания карты
    @PostMapping
    public ResponseEntity<CardResponseDTO> createCard(@Valid CardRequestDTO requestDTO) {
        try {
            CardResponseDTO createdCard = cardService.createCard(requestDTO);
            return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    //TODO: Реализовать пагинацию
    @GetMapping
    public ResponseEntity<List<CardResponseDTO>> getAllCards(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "1") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(cardService.getAllCards(pageable));
    }


    //TODO: Реализовать метод получения карты по её ID
    @GetMapping("/{id}")
    public CardResponseDTO getCardById(@PathVariable Long id) {
        return cardService.getCardById(id);
    }

    //TODO: Реализовать метод изменения полей карты по её ID
    @PutMapping("/{id}")
    public CardResponseDTO updateCard() {
        return null;
    }

    //TODO: Реализовать метод удаления карты по её ID
    @DeleteMapping("/{id}")
    public String deleteUser() {
        return null;
    }
}
