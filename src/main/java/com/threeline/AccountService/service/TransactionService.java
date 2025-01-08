package com.threeline.AccountService.service;

import com.threeline.AccountService.TransactionType;
import com.threeline.AccountService.entity.Account;
import com.threeline.AccountService.entity.Transaction;

import java.math.BigDecimal;

public interface TransactionService {
    Transaction logTransaction(Account account, BigDecimal transactionAmount, TransactionType transactionType);
}
