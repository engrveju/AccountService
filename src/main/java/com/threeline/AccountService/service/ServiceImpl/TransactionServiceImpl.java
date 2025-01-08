package com.threeline.AccountService.service.ServiceImpl;

import com.threeline.AccountService.TransactionType;
import com.threeline.AccountService.entity.Account;
import com.threeline.AccountService.entity.Transaction;
import com.threeline.AccountService.repository.TransactionRepository;
import com.threeline.AccountService.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction logTransaction(Account account, BigDecimal transactionAmount, TransactionType transactionType) {
       BigDecimal amountBefore =  transactionType.equals(TransactionType.CREDIT)
               ? account.getBalance().subtract(transactionAmount)
               : account.getBalance().add(transactionAmount);

        BigDecimal amountAfter =  transactionType.equals(TransactionType.CREDIT)
                ? account.getBalance()
                : account.getBalance().add(transactionAmount);

        Transaction transaction = Transaction.builder()
                .account(account)
                .transactionAmount(transactionAmount)
                .transactionType(transactionType)
                .amountBefore(amountBefore)
                .amountAfter(amountAfter)
                .build();

        try{
            return transactionRepository.save(transaction);
        }catch (DataIntegrityViolationException e){
            log.error("Error Occurred Creating TransactionLog: {}", e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
            //TODO: Proper Custom Exception is supposed to be created and handled using exception handlers. i skipped it due to time constraints
        }
    }
}