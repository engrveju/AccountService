package com.threeline.AccountService.util;

import com.threeline.AccountService.entity.Customer;
import com.threeline.AccountService.repository.CustomerRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabasePopulator {

    @Autowired
    private CustomerRepository customerRepository;

    @PostConstruct
    public void init() {
        if (customerRepository.count() == 0) {

            Customer customer1 = Customer.builder()
                    .name("Emma")
                    .surname("Ugwueze")
                    .email("emma@gmail.com")
                    .build();
            customerRepository.save(customer1);

            Customer customer2 = Customer.builder()
                    .name("Jideofor")
                    .surname("Eze")
                    .email("jide@gmail.com")
                    .build();
            customerRepository.save(customer2);
        }
    }
}
