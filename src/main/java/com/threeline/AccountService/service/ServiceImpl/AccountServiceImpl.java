package com.threeline.AccountService.service.ServiceImpl;

import com.threeline.AccountService.TransactionType;
import com.threeline.AccountService.dto.response.DefaultApiResponse;
import com.threeline.AccountService.entity.Account;
import com.threeline.AccountService.entity.Customer;
import com.threeline.AccountService.entity.Transaction;
import com.threeline.AccountService.repository.AccountRepository;
import com.threeline.AccountService.service.AccountService;
import com.threeline.AccountService.service.CustomerService;
import com.threeline.AccountService.service.TransactionService;
import com.threeline.AccountService.util.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    private final CustomerService customerService;

    @Override
    @Transactional
    public ResponseEntity<?> createAndFundAccount(Long customerId, BigDecimal amount) {
        Customer customer = customerService.getCustomer(customerId);
        if(customer==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AppUtils.defaultErrorResponse("Customer Not Found"));
        }

        Account account = createAccount(customer);
        Account creditedAccount = creditAccount(account,amount);
        if(amount.compareTo(BigDecimal.ZERO) > 0) {
            transactionService.logTransaction(creditedAccount, amount, TransactionType.CREDIT);
        }
        return ResponseEntity.ok(AppUtils.defaultSuccessResponse());
    }

    @Override
    public Account createAccount(Customer customer) {
        Account existingAccount = accountRepository.findByCustomerId(customer.getId()).orElse(null);
        if(existingAccount!=null){
            return existingAccount;
        }
        Account account = Account.builder()
                .customerId(customer.getId())
                .balance(BigDecimal.ZERO)
                .build();
        try {
            account = accountRepository.save(account);
            customerService.assignAccountToCustomer(account);
            return account;
        }catch (DataIntegrityViolationException e){
            log.error("Error Creating a new Account: {}", e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public Account creditAccount(Account account, BigDecimal amount) {
        BigDecimal balance = account.getBalance();
        BigDecimal newBalance = balance.add(amount);
        account.setBalance(newBalance);
        try {
            return accountRepository.save(account);
        }catch (DataIntegrityViolationException e){
            log.error("Error Crediting  Account for customer with Id: {}. Reason :{}",account.getCustomerId(), e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}