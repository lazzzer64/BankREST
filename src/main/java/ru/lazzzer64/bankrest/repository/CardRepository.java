package ru.lazzzer64.bankrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lazzzer64.bankrest.entity.Card;
import ru.lazzzer64.bankrest.entity.User;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByOwner(User user);
}
