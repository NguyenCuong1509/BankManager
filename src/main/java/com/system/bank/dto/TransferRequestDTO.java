package com.system.bank.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDTO {
    @NotBlank(message = "Số tài khoản chuyển không được để trống")
    private String formAccount;

    @NotBlank(message = "Số tài khoản nhận không được để trống")
    private String toAccount;

    @NotNull(message = "Số tiền chuyển không được để trống")
    @DecimalMin(value = "1000", message = "Số tiền chuyển tối thiểu là 1,000 VND")
    private BigDecimal amount;
}
