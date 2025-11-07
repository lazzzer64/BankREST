package ru.lazzzer64.bankrest.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lazzzer64.bankrest.dto.accountDTO.CardResponseDTO;
import ru.lazzzer64.bankrest.dto.accountDTO.CardUpdateDTO;
import ru.lazzzer64.bankrest.entity.Card;
import ru.lazzzer64.bankrest.entity.CardStatus;
import ru.lazzzer64.bankrest.entity.User;
import ru.lazzzer64.bankrest.repository.CardRepository;
import ru.lazzzer64.bankrest.repository.UserRepository;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    //CREATE - Создание новой карты при создании нового пользователя
    public CardResponseDTO createCard(Long userId) {
        Card card = new Card();
        card.setOwner(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(("Аккаунт с ID: " + userId + " не найден"))));
        card.setCardNumber(generateRandom16Digit());
        card.setExpiryDate("1010");
        card.setStatus(CardStatus.ACTIVE);

        Card savedCard = cardRepository.save(card);
        return convertToDTO(savedCard);
    }

    //READ - получить все карты по DTO
    public List<CardResponseDTO> getAllCards() {
        List<Card> listCard = cardRepository.findAll();
        List<CardResponseDTO> result = new ArrayList<>();
        for (Card card : listCard) {
            result.add(convertToDTO(card));
        }
        return result;
    }

    //READ - получить все карты полностью
    public List<Card> getAllCardFull() {
        return cardRepository.findAll();
    }

    //READ - получить карту по ее Id
    public CardResponseDTO getAccountDTOById(Long id) {
        Card findedCard = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(("Аккаунт с ID: " + id + " не найден")));
        return convertToDTO(findedCard);
    }

    //READ - получить карту по ее пользователю
    public CardResponseDTO getCardDTOByUser(User user) {
        Card findedCard = cardRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException(("Аккаунт связанный с: " + user.getUsername() + " не найден")));
        return convertToDTO(findedCard);
    }

    //READ - получить карту по имени пользователя
    public CardResponseDTO getCardDTOByUsername(String username) {
        Card findedCard = cardRepository.findById(userRepository.findByUsername(username).get().getId())
                .orElseThrow(() -> new RuntimeException(("Аккаунт связанный с: " + username + " не найден")));
        return convertToDTO(findedCard);
    }

    //UPDATE - Обновить данные карты
    public CardResponseDTO updateCard(Long id, CardUpdateDTO dto) {
        return null;
    }

    //DELETE - Удалить аккаунт
    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }

    private CardResponseDTO convertToDTO(Card card) {
        CardResponseDTO dto = new CardResponseDTO();
        dto.setId(card.getId());
        dto.setBalance(card.getBalance());
        dto.setCardStatus(card.getStatus());
        dto.setCardNumber(card.getMaskedCardNumber());
        dto.setOwner(card.getOwner());
        return dto;
    }

    // С использованием SecureRandom (рекомендуется)
    public static String generateRandom16Digit() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(16);

        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10)); // цифры 0-9
        }

        return sb.toString();
    }
}
