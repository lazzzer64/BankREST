package ru.lazzzer64.bankrest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lazzzer64.bankrest.entity.Card;
import ru.lazzzer64.bankrest.repository.CardRepository;

import java.math.BigDecimal;

@Service
@Transactional
public class CardService {

    @Autowired
    private CardRepository cardRepository;

//    @Autowired
//    private

    public Card createCard(Card card) {
        if(cardRepository.existsByCardNumber(card.getCardNumber())) {
            throw new RuntimeException("Карта с таким номером уже существует");
        }

        return cardRepository.save(card);
    }

    public Card updateBalance(Long cardId, BigDecimal amount) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        card.setBalance(card.getBalance().add(amount));

        return cardRepository.save(card);
    }

    // Валидация номера карты алгоритмом Луна
    private boolean isValidCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() != 16) {
            return false;
        }

        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
}