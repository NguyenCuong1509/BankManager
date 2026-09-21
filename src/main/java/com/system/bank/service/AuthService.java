package com.system.bank.service;

import com.system.bank.dto.RegisterRequestDTO;
import com.system.bank.entity.CustomerEntity;
import com.system.bank.entity.UserEntity;
import com.system.bank.enums.AccountCustomerStatus;
import com.system.bank.exception.AppException;
import com.system.bank.exception.ErrorCode;
import com.system.bank.repository.CustomerRepository;
import com.system.bank.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
  
}
