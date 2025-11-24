package ru.lazzzer64.bankrest.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.lazzzer64.bankrest.dto.cardDTO.CardResponseDTO;
import ru.lazzzer64.bankrest.dto.cardDTO.CardResponseNumberDTO;
import ru.lazzzer64.bankrest.dto.userDTO.UsernameResponseDTO;
import ru.lazzzer64.bankrest.entity.CardStatus;
import ru.lazzzer64.bankrest.service.CardService;
import ru.lazzzer64.bankrest.service.UserService;

import java.math.BigDecimal;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CardResponseDTO> createCard(@Valid
                                                      @RequestBody UsernameResponseDTO usernameResponseDTO) {
        try {
            CardResponseDTO createdCard = cardService.createCard(
                    userService.getByLogin(usernameResponseDTO.getUsername())
                            .getId());
            return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    //READ - Получить все карты
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<CardResponseNumberDTO>> getAllCards() {
        return ResponseEntity.ok(cardService.getAllCards());
    }

    //READ - Получить карту по имени пользователя
    @GetMapping("/{username}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<List<CardResponseNumberDTO>> getCardsByUsername(@PathVariable String username) {
        return ResponseEntity.ok(cardService.getCardsByUsername(username));
    }

    //UPDATE - Заблокировать карту
    @PutMapping("/{id}/block")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CardStatus> blockCard(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.blockCard(id).getCardStatus());
    }

    //UPDATE - Активировать карту
    @PutMapping("/{id}/active")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CardStatus> activeCard(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.activeCard(id).getCardStatus());
    }

    //UPDATE - Перевести деньги с карты на карту
    @PutMapping("/transactions/{amount}/{idCardSender}/{idCardRecipient}")
    public ResponseEntity transaction(@PathVariable(value = "amount", required = true) BigDecimal amount,
                                      @PathVariable(value = "idCardSender", required = true) Long idCardSender,
                                      @PathVariable(value = "idCardRecipient", required = true) Long idCardRecipient) {
        return ResponseEntity.ok(cardService.transaction(amount, idCardSender, idCardRecipient));
    }

    //DELETE - Удалить карту
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.ok("Карта с id: " + id + " удалена!");
    }
}
