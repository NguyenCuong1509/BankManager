package com.system.bank.dto;

import com.system.bank.entity.AccountEntity;
import com.system.bank.enums.AccountCustomerStatus;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReponseDTO {
    private Long customerId;
    private String fullName;
    private String phoneNumber;
    private AccountCustomerStatus customerStatus;
    private LocalDate dateOfBirth;
    private String address;
    private List<AccountReponseDTO> accounts;
}
