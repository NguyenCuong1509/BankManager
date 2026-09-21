package com.system.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDTO {
    private String fullName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String address;
}
