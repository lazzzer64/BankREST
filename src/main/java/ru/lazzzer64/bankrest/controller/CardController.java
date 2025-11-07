package ru.lazzzer64.bankrest.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lazzzer64.bankrest.dto.cardDTO.CardResponseDTO;
import ru.lazzzer64.bankrest.dto.cardDTO.CardUpdateDTO;
import ru.lazzzer64.bankrest.dto.userDTO.UserResponseDTO;
import ru.lazzzer64.bankrest.entity.User;
import ru.lazzzer64.bankrest.service.CardService;
import ru.lazzzer64.bankrest.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
public class CardController {
    private final CardService cardService;


    private final UserService userService;

    public CardController(CardService cardService, UserService userService) {
        this.cardService = cardService;
        this.userService = userService;
    }

    //CREATE - Создать карту
    @PostMapping
    public ResponseEntity<CardResponseDTO> createCard(@Valid
                                                         @RequestBody UserResponseDTO userResponseDTO) {
        try {
            CardResponseDTO createdCard = cardService.createCard(userResponseDTO.getId());
            return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    //READ - Получить все аккаунты
    @GetMapping
    public ResponseEntity<List<CardResponseDTO>> getAllCards() {
        return ResponseEntity.ok(cardService.getAllCards());
    }

    //READ - Получить все аккаунты с полной информацией
//    @GetMapping("full")
//    public ResponseEntity<List<Card>> getAllAccountsFull() {
//        return ResponseEntity.ok(cardService.getAllCards());
//    }

    //READ - Получить аккаунт по пользователю
    @GetMapping("/username")
    public ResponseEntity<CardResponseDTO> getCardByUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(cardService.getCardDTOByUser(user));
    }

    //READ - Получить аккаунт по имени пользователя
    @GetMapping("/findByUsername/{username}")
    public ResponseEntity<CardResponseDTO> getCardByUsername(@PathVariable String username) {
        return ResponseEntity.ok(cardService.getCardDTOByUsername(username));
    }

    //READ - получить аккаунт по его ID
    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDTO> getCardById(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.getAccountDTOById(id));
    }

    //UPDATE - Обновить данные аккаунта
    @PutMapping("/{id}")
    public ResponseEntity<CardResponseDTO> updateCard(@PathVariable Long id,
                                                         @Valid @RequestBody CardUpdateDTO accountUpdateDTO) {
        return ResponseEntity.ok(cardService.updateCard(id, accountUpdateDTO));
    }

    //DELETE - Удалить аккаунт
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.ok().build();
    }
}
