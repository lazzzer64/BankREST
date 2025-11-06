package ru.lazzzer64.bankrest.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lazzzer64.bankrest.dto.accountDTO.AccountResponseDTO;
import ru.lazzzer64.bankrest.dto.accountDTO.AccountUpdateDTO;
import ru.lazzzer64.bankrest.entity.Account;
import ru.lazzzer64.bankrest.entity.User;
import ru.lazzzer64.bankrest.repository.AccountRepository;
import ru.lazzzer64.bankrest.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    //CREATE - Создание нового аккаунта при создании нового пользователя
    public AccountResponseDTO createAccount(@Valid User user) {
        Account account = new Account();
        account.setUser(user);

        Account savedAccount = accountRepository.save(account);
        return convertToDTO(savedAccount);
    }

    //READ - получить все аккаунты по DTO
    public List<AccountResponseDTO> getAllAccounts() {
        List<Account> listAccount = accountRepository.findAll();
        List<AccountResponseDTO> result = new ArrayList<>();
        for (Account account : listAccount) {
            result.add(convertToDTO(account));
        }
        return result;
    }

    //READ - получить все аккаунты полностью
    public List<Account> getAllAccountFull() {
        return accountRepository.findAll();
    }

    //READ - получить аккаунт по его Id
    public AccountResponseDTO getAccountDTOById(Long id) {
        Account findedAccount = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(("Аккаунт с ID: " + id + " не найден")));
        return convertToDTO(findedAccount);
    }

    //READ - получить аккаунт по его пользователю
    public AccountResponseDTO getAccountDTOByUser(User user) {
        Account findedAccount = accountRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException(("Аккаунт связанный с: " + user.getUsername() + " не найден")));
        return convertToDTO(findedAccount);
    }

    //READ - получить аккаунт по имени пользователя
    public AccountResponseDTO getAccountDTOByUsername(String username) {
        Account findedAccount = accountRepository.findById(userRepository.findByUsername(username).get().getId())
                .orElseThrow(() -> new RuntimeException(("Аккаунт связанный с: " + username + " не найден")));
        return convertToDTO(findedAccount);
    }

    //UPDATE - Обновить данные аккаунта
    public AccountResponseDTO updateAccount(Long id, AccountUpdateDTO dto) {
        return null;
    }

    //DELETE - Удалить аккаунт
    public void deleteAccount(Long id) {
    }

    private AccountResponseDTO convertToDTO(Account account) {
        AccountResponseDTO dto = new AccountResponseDTO();
        dto.setId(account.getId());

        return dto;
    }
}
