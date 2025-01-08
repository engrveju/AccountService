package com.threeline.AccountService.service.ServiceImpl;

import com.threeline.AccountService.dto.response.DefaultApiResponse;
import com.threeline.AccountService.dto.response.UserDetailsDto;
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
import java.util.Collections;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class CustomerServiceIntegrationTest {

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    private Customer testCustomer;
    private Account testAccount;

    @BeforeEach
    void setup() {
        testCustomer = new Customer();
        testCustomer.setName("Emma");
        testCustomer.setSurname("Nuel");
        testCustomer.setEmail("jide@3line.com");
        customerRepository.save(testCustomer);

        testAccount = new Account();
        testAccount.setCustomerId(testCustomer.getId());
        testAccount.setBalance(new BigDecimal("1000.00"));
        testAccount.setTransactions(Collections.emptyList());
        accountRepository.save(testAccount);

        testCustomer.setAccountId(testAccount.getId());
        customerRepository.save(testCustomer);
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteById(testCustomer.getId());
        accountRepository.deleteByCustomerId(testCustomer.getId());
    }

    @Test
    void getCustomerDetails_shouldReturnCustomerDetailsSuccessfully() {
        ResponseEntity<?> response = customerService.getCustomerDetails(testCustomer.getId());

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        DefaultApiResponse apiResponse = (DefaultApiResponse) response.getBody();
        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getMessage()).isEqualTo(AppUtils.defaultSuccessResponse().getMessage());

        UserDetailsDto userDetails = (UserDetailsDto) apiResponse.getData();
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getName()).isEqualTo(testCustomer.getName());
        assertThat(userDetails.getSurname()).isEqualTo(testCustomer.getSurname());
        assertThat(userDetails.getBalance()).isEqualByComparingTo(testAccount.getBalance());
    }

    @Test
    void getCustomerDetails_shouldReturnErrorWhenCustomerNotFound() {
        Long invalidCustomerId = 999L;
        ResponseEntity<?> response = customerService.getCustomerDetails(invalidCustomerId);

        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
        DefaultApiResponse apiResponse = (DefaultApiResponse) response.getBody();
        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getMessage()).isEqualTo("Customer Not Found");
    }

    @Test
    void getCustomerDetails_shouldReturnErrorWhenNoAccountIsAssociated() {
        testCustomer.setAccountId(null);
        customerRepository.save(testCustomer);

        ResponseEntity<?> response = customerService.getCustomerDetails(testCustomer.getId());

        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
        DefaultApiResponse apiResponse = (DefaultApiResponse) response.getBody();
        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getMessage()).isEqualTo("No Account Associated To This Customer");
    }

    @Test
    void getCustomerDetails_shouldReturnErrorWhenAccountNotFound() {

        accountRepository.delete(testAccount);

        ResponseEntity<?> response = customerService.getCustomerDetails(testCustomer.getId());

        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
        DefaultApiResponse apiResponse = (DefaultApiResponse) response.getBody();
        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getMessage()).isEqualTo("Account Not Found");
    }
}
