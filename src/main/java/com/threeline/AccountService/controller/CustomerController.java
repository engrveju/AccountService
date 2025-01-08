package com.threeline.AccountService.controller;


import com.threeline.AccountService.dto.request.CustomerRegistrationRequest;
import com.threeline.AccountService.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/details/{customerId}")
    public ResponseEntity<?> getCustomerDetails(@PathVariable Long customerId) {
        return customerService.getCustomerDetails(customerId);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Validated CustomerRegistrationRequest request) {
        return customerService.register(request);
    }
}
