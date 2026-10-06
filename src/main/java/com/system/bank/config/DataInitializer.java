package com.system.bank.config;

import com.system.bank.entity.AccountEntity;
import com.system.bank.entity.CustomerEntity;
import com.system.bank.entity.RoleEntity;
import com.system.bank.entity.UserEntity;
import com.system.bank.enums.AccountCustomerStatus;
import com.system.bank.enums.AccountType;
import com.system.bank.repository.AccountRepository;
import com.system.bank.repository.CustomerRepository;
import com.system.bank.repository.RoleRepository;
import com.system.bank.repository.UserRepository;
import com.system.bank.service.CustomerService;
import org.apache.catalina.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DataInitializer {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    @Bean
    CommandLineRunner init(){
        return arg -> {
            initRoles();
            initCustomers();
            initUsers();
            initAccounts();
        };
    }

    private void initCustomers() {

        if (customerRepository.findByPhoneNumber("0900000001").isEmpty()) {

            CustomerEntity customer = new CustomerEntity();

            customer.setFullName("Nguyen Van A");
            customer.setPhoneNumber("0900000001");
            customer.setCustomerStatus(AccountCustomerStatus.ACTIVE);
            customer.setDateOfBirth(LocalDate.of(2000, 1, 1));
            customer.setAddress("Ha Noi");

            customerRepository.save(customer);
        }
    }
    private void initAccounts() {

        CustomerEntity customer = customerRepository
                .findByPhoneNumber("0900000001")
                .orElseThrow();

        if (accountRepository.findByAccountNumber("1000000001").isEmpty()) {
            AccountEntity account1 = new AccountEntity();
            account1.setAccountNumber("1000000001");
            account1.setBalance(BigDecimal.ZERO);
            account1.setAccountType(AccountType.PAYMENT);
            account1.setCustomer(customer);

            accountRepository.save(account1);
        }

        if (accountRepository.findByAccountNumber("1000000002").isEmpty()) {
            AccountEntity account2 = new AccountEntity();
            account2.setAccountNumber("1000000002");
            account2.setBalance(new BigDecimal("50000000"));
            account2.setAccountType(AccountType.PAYMENT);
            account2.setCustomer(customer);

            accountRepository.save(account2);
        }

        if (accountRepository.findByAccountNumber("1000000003").isEmpty()) {
            AccountEntity account3 = new AccountEntity();
            account3.setAccountNumber("1000000003");
            account3.setBalance(new BigDecimal("100000000"));
            account3.setAccountType(AccountType.SAVING);
            account3.setCustomer(customer);

            accountRepository.save(account3);
        }
    }
    private void initRoles(){
        if (roleRepository.findByNameRole("CUSTOMER").isEmpty()){
            RoleEntity entity = RoleEntity.builder()
                    .nameRole("CUSTOMER")
                    .build();
        roleRepository.save(entity);
        }
        if (roleRepository.findByNameRole("ADMIN").isEmpty()){
            RoleEntity entity = RoleEntity.builder()
                    .nameRole("ADMIN")
                    .build();
        roleRepository.save(entity);
        }
    }
    private void initUsers() {

        if (userRepository.findByUsername("ADMIN").isEmpty()) {

            RoleEntity adminRole = roleRepository
                    .findByNameRole("ADMIN")
                    .orElseThrow();

            UserEntity admin = UserEntity.builder()
                    .username("ADMIN")
                    .password(passwordEncoder.encode("123456"))
                    .role(adminRole)
                    .build();

            userRepository.save(admin);
        }

        if (userRepository.findByUsername("CUSTOMER").isEmpty()) {

            RoleEntity customerRole = roleRepository
                    .findByNameRole("CUSTOMER")
                    .orElseThrow();

            CustomerEntity customerEntity = customerRepository
                    .findByPhoneNumber("0900000001")
                    .orElseThrow();

            UserEntity customer = UserEntity.builder()
                    .username("CUSTOMER")
                    .password(passwordEncoder.encode("123456789"))
                    .role(customerRole)
                    .customer(customerEntity)
                    .build();

            userRepository.save(customer);
        }
    }
}
