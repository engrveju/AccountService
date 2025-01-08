package com.threeline.AccountService.entity;

import com.threeline.AccountService.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction extends BaseEntity{
    private BigDecimal transactionAmount;
    private BigDecimal amountBefore;
    private BigDecimal amountAfter;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
