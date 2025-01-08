package com.threeline.AccountService.controller;

import com.threeline.AccountService.dto.request.AccountCreationDto;
import com.threeline.AccountService.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/deposit")
    public ResponseEntity<?> createAccount(@RequestBody @Valid AccountCreationDto request) {
        return accountService.createAndFundAccount(request.customerId(),request.initialCredit());
    }
}
