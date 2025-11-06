package ru.lazzzer64.bankrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lazzzer64.bankrest.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
