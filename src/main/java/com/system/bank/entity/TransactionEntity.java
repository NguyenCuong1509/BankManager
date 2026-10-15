package com.system.bank.entity;

import com.system.bank.enums.TransactionStatus;
import com.system.bank.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSACTION_ENTITY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    @SequenceGenerator(name = "transaction_seq", sequenceName = "TRANSACTION_SEQ", allocationSize = 1)
    @Column(name = "TRANSACTION_ID")
    Long transactionId;

    @Column(name = "REFNO", unique = true)
    String refNo;

    @Column(name = "FROM_ACCOUNT")
    String fromAccount;

    @Column(name = "TO_ACCOUNT")
    String toAccount;

    @Column(name = "AMOUNT")
    BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    TransactionStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "TRANS_TYPE")
    TransactionType transType;

    @Column(name = "CREATE_AT")
    LocalDateTime createAt = LocalDateTime.now();

    @Column(name = "FAILURE_REASON")
    String failureReason;

    @Column(name = "ACCOUNT_ID")
    Long accountId;
}
