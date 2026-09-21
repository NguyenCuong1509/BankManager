package com.system.bank.entity;

import com.system.bank.enums.AccountCustomerStatus;
import com.system.bank.enums.AccountType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "account")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountId")
    Long accountId;

    @Column(name = "AccountNumber")
    String accountNumber;

    @Column(name = "Balance")
    BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "AccountStatus")
    AccountCustomerStatus accountStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "AccountType")
    AccountType accountType;

    @Column(name = "CreateAt")
    LocalDateTime createAt = LocalDateTime.now();

    @Column(name = "DailyLimit")
    BigDecimal dailyLimit;

    @ManyToOne
    @JoinColumn(name = "CustomerId")
    CustomerEntity customer;
}
