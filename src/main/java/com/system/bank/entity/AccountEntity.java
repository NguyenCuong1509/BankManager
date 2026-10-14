package com.system.bank.entity;

import com.system.bank.enums.AccountCustomerStatus;
import com.system.bank.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ACCOUNT")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_req")
    @SequenceGenerator(name = "account_req", sequenceName = "ACCOUNT_SEQ", allocationSize = 1)
    @Column(name = "ACCOUNT_ID")
    Long accountId;

    @Column(name = "ACCOUNT_NUMBER")
    String accountNumber;

    @Column(name = "BALANCE")
    BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_STATUS")
    AccountCustomerStatus accountStatus = AccountCustomerStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_TYPE")
    AccountType accountType;

    @Column(name = "CREATE_AT")
    LocalDateTime createAt = LocalDateTime.now();

    @Column(name = "DAILY_LIMIT")
    BigDecimal dailyLimit;

    @Column(name = "CUSTOMER_ID")
    Long customerId;
}
