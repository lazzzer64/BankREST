package ru.lazzzer64.bankrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lazzzer64.bankrest.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {

}
