package com.threeline.AccountService.repository;


import com.threeline.AccountService.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    boolean existsByEmail(String email);
}
