package com.threeline.AccountService.repository;


import com.threeline.AccountService.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
