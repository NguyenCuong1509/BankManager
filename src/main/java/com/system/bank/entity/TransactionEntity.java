package com.system.bank.entity;

import com.system.bank.enums.TransactionStatus;
import com.system.bank.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionId")
    Long transactionId;

    @Column(name = "RefNo", unique = true)
    String refNo;

    @Column(name = "FromAccount")
    String fromAccount;

    @Column(name = "ToAccount")
    String toAccount;

    @Column(name = "Amount")
    BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    TransactionStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "TranType")
    TransactionType tranType;

    @Column(name = "CreateAt")
    LocalDateTime createAt = LocalDateTime.now();

    @Column(name = "FailureReason")
    String failureReason;

    @ManyToOne
    @JoinColumn(name = "AccountId")
    AccountEntity account;
}
