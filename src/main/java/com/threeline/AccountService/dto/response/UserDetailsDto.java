package com.threeline.AccountService.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {
    private String name;
    private String surname;
    private BigDecimal balance;
    private List<TransactionDetailsDto> transactions;
}
