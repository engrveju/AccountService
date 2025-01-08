package com.threeline.AccountService.repository;

import com.threeline.AccountService.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByCustomerId(long customerId);
}
