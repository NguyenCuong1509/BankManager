package com.system.bank.service;

import com.system.bank.dto.AccountReponseDTO;
import com.system.bank.dto.AccountRequestDTO;
import com.system.bank.dto.CustomerReponseDTO;
import com.system.bank.dto.CustomerRequestDTO;
import com.system.bank.entity.AccountEntity;
import com.system.bank.entity.CustomerEntity;
import com.system.bank.entity.PaymentAccountEntity;
import com.system.bank.entity.SavingAccountEntity;
import com.system.bank.enums.AccountCustomerStatus;
import com.system.bank.enums.AccountType;
import com.system.bank.exception.AppException;
import com.system.bank.exception.ErrorCode;
import com.system.bank.mapper.AccountMapper;
import com.system.bank.mapper.CustomerMapper;
import com.system.bank.repository.AccountRepository;
import com.system.bank.repository.CustomerRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final CustomerMapper customerMapper;
    private final AccountMapper accountMapper;

    public CustomerService(CustomerRepository customerRepository, AccountRepository accountRepository,
            CustomerMapper customerMapper, AccountMapper accountMapper) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.customerMapper = customerMapper;
        this.accountMapper = accountMapper;
    }

    @Transactional(readOnly = true)
    public List<CustomerReponseDTO> findAllCustomer() {
        return customerRepository.findAllWithAccount().stream()
                .map(customerMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustomerReponseDTO getCustomerById(Long customerId) {
        CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return customerMapper.toResponseDto(customer);
    }

    @Transactional
    public CustomerReponseDTO update(Long Id, CustomerRequestDTO update) {
        CustomerEntity customerUpdate = customerRepository.findById(Id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        customerMapper.mapperUpdate(update, customerUpdate);
        CustomerEntity saved = customerRepository.save(customerUpdate);
        return customerMapper.toResponseDto(saved);
    }

    @Transactional
    public CustomerReponseDTO create(CustomerRequestDTO request) {
        CustomerEntity entity = customerMapper.toEntity(request);
        CustomerEntity saved = customerRepository.save(entity);
        CustomerReponseDTO dto = customerMapper.toResponseDto(saved);
        return dto;
    }

    @Transactional(readOnly = true)
    public List<CustomerReponseDTO> getAll() {
        return customerRepository.findAllWithAccount().stream()
                .map(customerMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustomerReponseDTO findCustomer(Long id) {
        CustomerEntity entity = customerRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return customerMapper.toResponseDto(entity);
    }

    @Transactional
    public void lock(Long id) {
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        customer.setCustomerStatus(AccountCustomerStatus.LOCKED);
        customerRepository.save(customer);
    }

    // tài khoản
    @Transactional(readOnly = true)
    public List<AccountReponseDTO> getAllAccount(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }

        return accountRepository.findByCustomer_CustomerId(customerId).stream()
                .map(accountMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public AccountReponseDTO createAccount(AccountRequestDTO request) {
        CustomerEntity customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        AccountEntity entity;
        if (request.getAccountType() == AccountType.SAVING) {
            SavingAccountEntity saving = new SavingAccountEntity();

            saving.setAccountType(AccountType.SAVING);
            saving.setInterestRate(request.getInterestRate());
            saving.setTermMonths(request.getTermMonths());
            saving.setMaturityDate(request.getMaturityDate());
            entity = saving;
        } else if (request.getAccountType() == AccountType.PAYMENT) {
            PaymentAccountEntity payment = new PaymentAccountEntity();
            payment.setAccountType(AccountType.PAYMENT);
            payment.setDailyTransferred(request.getDailyTransferred());
            payment.setTransferCountToday(request.getTransferCountToday());
            entity = payment;
        } else {
            throw new AppException(ErrorCode.ACCOUNT_TYPE_INVALID);
        }
        entity.setAccountNumber(request.getAccountNumber());
        entity.setDailyLimit(request.getDailyLimit());
        entity.setCustomer(customer);
        AccountEntity saved = accountRepository.save(entity);
        return accountMapper.toResponseDto(saved);
    }

    @Transactional(readOnly = true)
    public AccountReponseDTO findByAccountId(Long id) {
        AccountEntity account = accountRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        return accountMapper.toResponseDto(account);
    }

    @Transactional(rollbackFor = Exception.class)
    public AccountReponseDTO updateAccountStatus(Long accountId, AccountCustomerStatus status) {
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        account.setAccountStatus(status);
        AccountEntity updated = accountRepository.save(account);
        return accountMapper.toResponseDto(updated);
    }
}