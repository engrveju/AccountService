package com.threeline.AccountService.service;

import com.threeline.AccountService.dto.request.CustomerRegistrationRequest;
import com.threeline.AccountService.entity.Account;
import com.threeline.AccountService.entity.Customer;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<?>  register(CustomerRegistrationRequest request);
    Customer getCustomer(Long customerId);
    void assignAccountToCustomer(Account account);

    ResponseEntity<?> getCustomerDetails(Long customerId);
}
