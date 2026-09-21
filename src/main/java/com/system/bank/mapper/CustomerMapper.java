package com.system.bank.mapper;

import com.system.bank.dto.AccountReponseDTO;
import com.system.bank.dto.CustomerReponseDTO;
import com.system.bank.dto.CustomerRequestDTO;
import com.system.bank.entity.CustomerEntity;
import com.system.bank.enums.AccountCustomerStatus;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {

    private final AccountMapper accountMapper;

    public CustomerMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    // DTO to Entity
    public CustomerEntity toEntity(CustomerRequestDTO customerRequestDTO) {
        if (customerRequestDTO == null) return null;
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFullName(customerRequestDTO.getFullName());
        customerEntity.setPhoneNumber(customerRequestDTO.getPhoneNumber());
        if (customerRequestDTO.getDateOfBirth() != null) {
            customerEntity.setDateOfBirth(customerRequestDTO.getDateOfBirth());
        }
        customerEntity.setAddress(customerRequestDTO.getAddress());
        customerEntity.setCustomerStatus(AccountCustomerStatus.ACTIVE);
        return customerEntity;
    }

    // Entity to Response DTO (kèm danh sách tài khoản nếu có)
    public CustomerReponseDTO toResponseDto(CustomerEntity entity) {
        if (entity == null) return null;
        CustomerReponseDTO response = new CustomerReponseDTO();
        response.setCustomerId(entity.getCustomerId());
        response.setFullName(entity.getFullName());
        response.setPhoneNumber(entity.getPhoneNumber());
        response.setCustomerStatus(entity.getCustomerStatus());
        if (entity.getDateOfBirth() != null) {
            response.setDateOfBirth(entity.getDateOfBirth());
        }
        response.setAddress(entity.getAddress());

        if (entity.getAccount() != null) {
            List<AccountReponseDTO> accountDTOs = entity.getAccount().stream()
                    .map(accountMapper::toResponseDto)
                    .collect(Collectors.toList());
            response.setAccounts(accountDTOs);
        } else {
            response.setAccounts(Collections.emptyList());
        }

        return response;
    }

    public void mapperUpdate(CustomerRequestDTO requestDTO, CustomerEntity entity) {
        if (requestDTO == null || entity == null) return;
        entity.setFullName(requestDTO.getFullName());
        entity.setPhoneNumber(requestDTO.getPhoneNumber());
        if (requestDTO.getDateOfBirth() != null) {
            entity.setDateOfBirth(requestDTO.getDateOfBirth());
        }
        entity.setAddress(requestDTO.getAddress());
    }
}
