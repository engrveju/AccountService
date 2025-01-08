package com.threeline.AccountService.service.ServiceImpl;

import com.threeline.AccountService.dto.request.CustomerRegistrationRequest;
import com.threeline.AccountService.dto.response.TransactionDetailsDto;
import com.threeline.AccountService.dto.response.UserDetailsDto;
import com.threeline.AccountService.entity.Account;
import com.threeline.AccountService.entity.Customer;
import com.threeline.AccountService.entity.Transaction;
import com.threeline.AccountService.repository.AccountRepository;
import com.threeline.AccountService.repository.CustomerRepository;
import com.threeline.AccountService.service.CustomerService;
import com.threeline.AccountService.util.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    @Override
    public ResponseEntity<?> register(CustomerRegistrationRequest request) {
        if(customerRepository.existsByEmail(request.email())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AppUtils.defaultErrorResponse("Customer Already Exists"));

        }

        Customer customer = Customer.builder()
                .name(request.name())
                .surname(request.surName())
                .email(request.email())
                .build();
        customer = customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.OK).body(AppUtils.defaultSuccessResponse(customer));

    }

    @Override
    public Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }

    @Override
    public void assignAccountToCustomer(Account account) {
        Customer  customer = getCustomer(account.getCustomerId());
        customer.setAccountId(account.getId());
        customerRepository.save(customer);
    }

    @Override
    public ResponseEntity<?> getCustomerDetails(Long customerId) {

        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AppUtils.defaultErrorResponse("Customer Not Found"));
        }

        if(customer.getAccountId()==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AppUtils.defaultErrorResponse("No Account Associated To This Customer"));
        }
        Account account = accountRepository.findById(customer.getAccountId()).orElse(null);

        if(account==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AppUtils.defaultErrorResponse("Account Not Found"));
        }
        List<Transaction> transactionList = account.getTransactions();
        UserDetailsDto userDetailsDto = UserDetailsDto.builder()
                .balance(account.getBalance())
                .name(customer.getName())
                .surname(customer.getSurname())
                .transactions(mapToTransactionDetailsDto(transactionList, account.getId()))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(AppUtils.defaultSuccessResponse(userDetailsDto));
    }

    private List<TransactionDetailsDto> mapToTransactionDetailsDto(List<Transaction> transactions,Long accountId){
       List<TransactionDetailsDto> transactionDetailsDtos = new ArrayList<>();
        for(Transaction transaction : transactions){
            TransactionDetailsDto transactionDetailsDto = TransactionDetailsDto.builder()
                    .id(transaction.getId())
                    .transactionType(transaction.getTransactionType())
                    .transactionAmount(transaction.getTransactionAmount())
                    .amountBefore(transaction.getAmountBefore())
                    .amountAfter(transaction.getAmountAfter())
                    .transactionDate(transaction.getCreatedAt())
                    .accountId(accountId)
                    .build();
            transactionDetailsDtos.add(transactionDetailsDto);
        }
        return transactionDetailsDtos;
    }
}