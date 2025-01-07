package com.threeline.AccountService.entity;

import com.threeline.AccountService.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction extends BaseEntity{
    private String accountId;
    private BigDecimal transactionAmount;
    private BigDecimal amountBefore;
    private BigDecimal amountAfter;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

}
