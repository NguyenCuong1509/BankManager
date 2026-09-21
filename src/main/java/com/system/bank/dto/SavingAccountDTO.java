package com.system.bank.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavingAccountDTO {
    private BigDecimal interestRate;
    private int termMonths;
    private LocalDate maturityDate;
}
