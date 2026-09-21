package com.system.bank.service;

import com.system.bank.dto.AccountReponseDTO;
import com.system.bank.dto.AccountRequestDTO;
import com.system.bank.dto.CustomerReponseDTO;
import com.system.bank.dto.CustomerRequestDTO;
import com.system.bank.entity.AccountEntity;
import com.system.bank.entity.CustomerEntity;
import com.system.bank.enums.AccountCustomerStatus;
import com.system.bank.exception.AppException;
import com.system.bank.exception.ErrorCode;
import com.system.bank.mapper.AccountMapper;
import com.system.bank.mapper.CustomerMapper;
import com.system.bank.repository.AccountRepository;
import com.system.bank.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository,
                           AccountRepository accountRepository,
                           CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.customerMapper = customerMapper;
    }

}