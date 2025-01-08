package com.threeline.AccountService.service;

import com.threeline.AccountService.entity.Account;
import com.threeline.AccountService.entity.Customer;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface AccountService {
    ResponseEntity<?> createAndFundAccount(Long customerId, BigDecimal amount);

    Account createAccount(Customer customer);

    Account creditAccount(Account account,BigDecimal amount);
}
