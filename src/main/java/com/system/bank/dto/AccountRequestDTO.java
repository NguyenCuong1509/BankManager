package com.system.bank.dto;

import com.system.bank.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDTO {
    private String accountNumber;
    private AccountType accountType;
    private BigDecimal dailyLimit;
    private Long customerId;
    private BigDecimal dailyTransferred;
    private int transferCountToday;
    private BigDecimal interestRate;
    private int termMonths;
    private LocalDate maturityDate;
}
