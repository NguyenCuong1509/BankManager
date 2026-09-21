package com.system.bank.mapper;

import com.system.bank.dto.AccountReponseDTO;
import com.system.bank.dto.AccountRequestDTO;
import com.system.bank.entity.AccountEntity;
import com.system.bank.entity.CustomerEntity;
import com.system.bank.enums.AccountCustomerStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class AccountMapper {

    public AccountEntity toEntity(AccountRequestDTO requestDTO, CustomerEntity customer) {
        if (requestDTO == null) return null;
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccountNumber(requestDTO.getAccountNumber());
        accountEntity.setAccountType(requestDTO.getAccountType());
        accountEntity.setDailyLimit(requestDTO.getDailyLimit());
        accountEntity.setBalance(BigDecimal.ZERO);
        accountEntity.setAccountStatus(AccountCustomerStatus.ACTIVE);
        accountEntity.setCreateAt(LocalDateTime.now());
        accountEntity.setCustomer(customer);
        return accountEntity;
    }

    public AccountReponseDTO toResponseDto(AccountEntity entity) {
        if (entity == null) return null;
        AccountReponseDTO dto = new AccountReponseDTO();
        dto.setAccountId(entity.getAccountId());
        dto.setAccountNumber(entity.getAccountNumber());
        dto.setBalance(entity.getBalance());
        dto.setAccountStatus(entity.getAccountStatus());
        dto.setAccountType(entity.getAccountType());
        dto.setCreateAt(entity.getCreateAt());
        dto.setDailyLimit(entity.getDailyLimit());
        if (entity.getCustomer() != null) {
            dto.setCustomerId(entity.getCustomer().getCustomerId());
        }
        return dto;
    }
}
