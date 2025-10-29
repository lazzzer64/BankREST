package ru.lazzzer64.bankrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lazzzer64.bankrest.entity.Card;
import ru.lazzzer64.bankrest.entity.CardStatus;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByCardNumber(String cardNumber);

    List<Card> findByCardHolderContainingIgnoreCase(String cardHolder);

    List<Card> findByStatus(CardStatus status);

    boolean existsByCardNumber(String cardNumber);
}
