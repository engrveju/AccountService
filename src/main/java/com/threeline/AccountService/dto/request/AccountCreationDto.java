package com.threeline.AccountService.dto.request;

import java.math.BigDecimal;


public record AccountCreationDto(
        String customerId,
        BigDecimal initialCredit
) {
}
