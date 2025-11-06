package ru.lazzzer64.bankrest.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lazzzer64.bankrest.dto.accountDTO.AccountResponseDTO;
import ru.lazzzer64.bankrest.dto.accountDTO.AccountUpdateDTO;
import ru.lazzzer64.bankrest.entity.Account;
import ru.lazzzer64.bankrest.entity.User;
import ru.lazzzer64.bankrest.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //CREATE - Создать аккаунт
    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@Valid
                                                            @RequestBody User user) {
        try {
            AccountResponseDTO createdAccount = accountService.createAccount(user);
            return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    //READ - Получить все аккаунты
    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    //READ - Получить все аккаунты с полной информацией
    @GetMapping("full")
    public ResponseEntity<List<Account>> getAllAccountsFull() {
        return ResponseEntity.ok(accountService.getAllAccountFull());
    }

    //READ - Получить аккаунт по пользователю
    @GetMapping("/username")
    public ResponseEntity<AccountResponseDTO> getAccountByUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(accountService.getAccountDTOByUser(user));
    }

    //READ - Получить аккаунт по имени пользователя
    @GetMapping("/findByUsername/{username}")
    public ResponseEntity<AccountResponseDTO> getAccountByUsername(@PathVariable String username) {
        return ResponseEntity.ok(accountService.getAccountDTOByUsername(username));
    }

    //READ - получить аккаунт по его ID
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountDTOById(id));
    }

    //UPDATE - Обновить данные аккаунта
    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable Long id,
                                                            @Valid @RequestBody AccountUpdateDTO accountUpdateDTO) {
        return ResponseEntity.ok(accountService.updateAccount(id, accountUpdateDTO));
    }

    //DELETE - Удалить аккаунт
    @DeleteMapping("/{id}")
    public ResponseEntity deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }
}
