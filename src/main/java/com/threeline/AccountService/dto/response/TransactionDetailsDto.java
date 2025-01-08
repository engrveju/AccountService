package com.threeline.AccountService.dto.response;

import com.threeline.AccountService.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailsDto {
    private Long id;
    private LocalDateTime transactionDate;
    private Long accountId;
    private BigDecimal transactionAmount;
    private BigDecimal amountBefore;
    private BigDecimal amountAfter;
    private TransactionType transactionType;
}
