
package com.threeline.AccountService.service.ServiceImpl;

import com.threeline.AccountService.dto.response.DefaultApiResponse;
import com.threeline.AccountService.entity.Account;
import com.threeline.AccountService.entity.Customer;
import com.threeline.AccountService.repository.AccountRepository;
import com.threeline.AccountService.repository.CustomerRepository;
import com.threeline.AccountService.util.AppUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class AccountServiceIntegrationTest {

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;


    private Customer testCustomer;

    @BeforeEach
    void setup() {
        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setName("Test");
        testCustomer.setSurname("Customer");
        testCustomer.setEmail("test@3line.com");
        customerRepository.save(testCustomer);
    }


    @AfterEach
    void tearDown() {
        customerRepository.deleteById(1L);
    }

    @Test
    void createAndFundAccount_shouldCreateAndFundAccountSuccessfully() {
        Long customerId = testCustomer.getId();
        BigDecimal initialAmount = new BigDecimal("1000.00");

        ResponseEntity<?> response = accountService.createAndFundAccount(customerId, initialAmount);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        DefaultApiResponse apiResponse = (DefaultApiResponse) response.getBody();
        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getMessage()).isEqualTo(AppUtils.defaultSuccessResponse().getMessage());

        Account createdAccount = accountRepository.findByCustomerId(customerId).orElse(null);
        assertThat(createdAccount).isNotNull();
        assertThat(createdAccount.getBalance()).isEqualByComparingTo(initialAmount);
    }

    @Test
    void createAndFundAccount_shouldReturnErrorWhenCustomerNotFound() {
        Long invalidCustomerId = 299L;
        BigDecimal initialAmount = new BigDecimal("1000.00");

        ResponseEntity<?> response = accountService.createAndFundAccount(invalidCustomerId, initialAmount);

        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
        DefaultApiResponse apiResponse = (DefaultApiResponse) response.getBody();
        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getMessage()).isEqualTo("Customer Not Found");
    }

    @Test
    void createAndFundAccount_shouldCreditExistingAccount() {
        Account existingAccount = new Account();
        existingAccount.setCustomerId(testCustomer.getId());
        existingAccount.setBalance(BigDecimal.ZERO);
        accountRepository.save(existingAccount);

        BigDecimal initialBalance = existingAccount.getBalance();

        BigDecimal additionalAmount = new BigDecimal("500.00");

        ResponseEntity<?> response = accountService.createAndFundAccount(testCustomer.getId(), additionalAmount);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        Account updatedAccount = accountRepository.findByCustomerId(testCustomer.getId()).orElse(null);
        assertThat(updatedAccount).isNotNull();
        assertThat(updatedAccount.getBalance()).isEqualByComparingTo(additionalAmount);
        assertThat(updatedAccount.getBalance()).isEqualByComparingTo(initialBalance.add(additionalAmount));

    }
}
