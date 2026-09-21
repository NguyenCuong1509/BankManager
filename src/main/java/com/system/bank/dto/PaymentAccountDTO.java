package com.system.bank.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAccountDTO {
    private BigDecimal dailyTransferred;
    private int transferCountToday;

}
