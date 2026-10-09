package com.system.bank.entity;

import com.system.bank.enums.TransactionStatus;
import com.system.bank.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    @SequenceGenerator(name = "transaction_seq", sequenceName = "TRANSACTION_SEQ", allocationSize = 1)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AccountId")
    AccountEntity account;
}
