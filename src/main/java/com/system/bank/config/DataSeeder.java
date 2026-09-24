package com.system.bank.config;


import com.system.bank.entity.AccountEntity;
import com.system.bank.entity.CustomerEntity;
import com.system.bank.entity.UserEntity;
import com.system.bank.enums.AccountCustomerStatus;
import com.system.bank.enums.AccountType;
import com.system.bank.enums.Role;
import com.system.bank.repository.AccountRepository;
import com.system.bank.repository.CustomerRepository;
import com.system.bank.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.xml.stream.events.Comment;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Slf4j
public class DataSeeder implements CommandLineRunner {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(CustomerRepository customerRepository, AccountRepository accountRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
        // 1. Khởi tạo tài khoản Admin hệ thống mẫu
        if (!userRepository.existsByUsername("admin")) {
            UserEntity admin = UserEntity.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
            log.info("==================================================================");
            log.info("[SEED DATA] Đã tạo tài khoản ADMIN thành công:");
            log.info("   -> Username: admin");
            log.info("   -> Password: admin123");
            log.info("==================================================================");
        }

        // 2. Khởi tạo tài khoản Khách hàng (User) kèm tài khoản ngân hàng mẫu
        if (!userRepository.existsByUsername("nguyenvana")) {
            CustomerEntity customer = new CustomerEntity();
            customer.setFullName("Nguyễn Văn A");
            customer.setPhoneNumber("0988889999");
            customer.setDateOfBirth(LocalDate.of(1995, 5, 20));
            customer.setAddress("Hà Nội, Việt Nam");
            customer.setCustomerStatus(AccountCustomerStatus.ACTIVE);
            CustomerEntity savedCustomer = customerRepository.save(customer);

            UserEntity customerUser = UserEntity.builder()
                    .username("nguyenvana")
                    .password(passwordEncoder.encode("123456"))
                    .role(Role.CUSTOMER)
                    .customer(savedCustomer)
                    .build();
            userRepository.save(customerUser);

            if (accountRepository.findByAccountNumber("1019998888").isEmpty()) {
                AccountEntity account = new AccountEntity();
                account.setAccountNumber("1019998888");
                account.setBalance(new BigDecimal("5000000.00")); // Số dư 5.000.000 VND
                account.setAccountStatus(AccountCustomerStatus.ACTIVE);
                account.setAccountType(AccountType.PAYMENT);
                account.setCreateAt(LocalDateTime.now());
                account.setDailyLimit(new BigDecimal("50000000.00")); // Hạn mức 50.000.000 VND
                account.setCustomer(savedCustomer);
                accountRepository.save(account);
            }

            log.info("==================================================================");
            log.info("[SEED DATA] Đã tạo tài khoản KHÁCH HÀNG mẫu thành công:");
            log.info("   -> Username: nguyenvana");
            log.info("   -> Password: 123456");
            log.info("   -> Họ tên:   Nguyễn Văn A");
            log.info("   -> Số TK:    1019998888 (Số dư: 5,000,000 VND)");
            log.info("==================================================================");
        }
    }
}
