package ru.lazzzer64.bankrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lazzzer64.bankrest.entity.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

}
