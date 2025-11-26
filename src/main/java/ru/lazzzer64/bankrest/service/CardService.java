package ru.lazzzer64.bankrest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lazzzer64.bankrest.dto.cardDTO.CardResponseDTO;
import ru.lazzzer64.bankrest.dto.cardDTO.CardResponseNumberDTO;
import ru.lazzzer64.bankrest.entity.Card;
import ru.lazzzer64.bankrest.entity.CardStatus;
import ru.lazzzer64.bankrest.entity.User;
import ru.lazzzer64.bankrest.repository.CardRepository;
import ru.lazzzer64.bankrest.repository.UserRepository;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CardService {
    private final CardRepository cardRepository;

    private final UserRepository userRepository;

    public CardService(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

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
    public List<CardResponseNumberDTO> getAllCards() {
        List<Card> listCard = cardRepository.findAll();
        List<CardResponseNumberDTO> result = new ArrayList<>();
        for (Card card : listCard) {
            result.add(converToNumberDTO(card));
        }
        return result;
    }

    //READ - получить все карты полностью
    public List<Card> getAllCardFull() {
        return cardRepository.findAll();
    }

    //READ - получить карту по ее Id
    public CardResponseDTO getCardDTOById(Long id) {
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

    //READ - получить карты по имени пользователя
    public List<CardResponseNumberDTO> getCardsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        List<Card> cards = cardRepository.findByOwner(user);

        return cards.stream()
                .map(this::converToNumberDTO)
                .collect(Collectors.toList());
    }

    //READ - получить карту по ее ID
    public Card getCardById(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(("Карта с id: " + id + " не найдена")));
    }

    //UPDATE - Изменить баланс данные карты
    private void changeCardBalance(Long id, BigDecimal amount) {
        Card card = getCardById(id);
        card.setBalance(card.getBalance().add(amount));
        cardRepository.save(card);
        convertToDTO(card);
    }

    //UPDATE - Заблокировать карту
    public CardResponseDTO blockCard(Long id) {
        Card card = new Card();
        card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Карта не найдена"));
        card.setStatus(CardStatus.BLOCKED);
        cardRepository.save(card);
        return convertToDTO(card);
    }

    //UPDATE - Активировать карту
    public CardResponseDTO activeCard(Long id) {
        Card card = new Card();
        card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Карта не найдена"));
        card.setStatus(CardStatus.ACTIVE);
        cardRepository.save(card);
        return convertToDTO(card);
    }

    //UPDATE - Перевести деньги с карты на карту
    public boolean transaction(BigDecimal amount, Long idCardSender, Long idCardRecipient) {
//        if ((checkCardBalance(idCardSender, amount))) {
        changeCardBalance(idCardSender, amount.negate());
        changeCardBalance(idCardRecipient, amount);
        return true;
//        } else
//            return false;
    }

    //DELETE - Удалить карту
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

    private CardResponseNumberDTO converToNumberDTO(Card card) {
        CardResponseNumberDTO dto = new CardResponseNumberDTO();
        dto.setCardNumber(card.getCardNumber());
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


    public Long getCountOfAllCards() {
        return (long) cardRepository.findAll().size();
    }

    private boolean checkCardBalance(Long id, BigDecimal amount) {
        Card card = getCardById(id);
        if (card.getBalance().compareTo(amount) >= 0)
            return true;
        else
            return false;
    }

}
