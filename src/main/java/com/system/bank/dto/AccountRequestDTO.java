package com.system.bank.dto;

import com.system.bank.enums.AccountCustomerStatus;
import com.system.bank.enums.AccountType;
import jakarta.persistence.*;
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
public class AccountRequestDTO {
    private String accountNumber;
    private AccountType accountType;
    private BigDecimal dailyLimit;
    private Long customerId;
}
