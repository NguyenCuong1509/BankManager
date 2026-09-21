package com.system.bank.dto;

import com.system.bank.enums.TransactionStatus;
import com.system.bank.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDTO {
    private Long transactionId;
    private String refNo;
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private TransactionStatus status;
    private TransactionType tranType;
    private LocalDateTime createAt;
    private String failureReason;
}
