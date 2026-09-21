package com.system.bank.dto;

import com.system.bank.enums.AccountCustomerStatus;
import com.system.bank.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class AccountReponseDTO {
    private Long accountId;
    private String accountNumber;
    private BigDecimal balance;
    private AccountCustomerStatus accountStatus;
    private AccountType accountType;
    private LocalDateTime createAt;
    private BigDecimal dailyLimit;
    private Long customerId;
}