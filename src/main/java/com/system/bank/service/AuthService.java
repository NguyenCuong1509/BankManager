package com.system.bank.service;

import com.system.bank.dto.LoginRequestDTO;
import com.system.bank.dto.LoginResponseDTO;
import com.system.bank.dto.RegisterRequestDTO;
import com.system.bank.entity.CustomerEntity;
import com.system.bank.entity.UserEntity;
import com.system.bank.enums.AccountCustomerStatus;
import com.system.bank.enums.Role;
import com.system.bank.exception.AppException;
import com.system.bank.exception.ErrorCode;
import com.system.bank.repository.AccountRepository;
import com.system.bank.repository.CustomerRepository;
import com.system.bank.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final com.system.bank.securityConfig.JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       CustomerRepository customerRepository,
                       PasswordEncoder passwordEncoder,
                       com.system.bank.securityConfig.JwtService jwtService) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO request){
        UserEntity entity = userRepository.findByUsername(request.getUsername()).orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
        boolean matchs = passwordEncoder.matches(request.getPassword(), entity.getPassword());
        if (!matchs){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        if (entity.getCustomer() != null && entity.getCustomer().getCustomerStatus() == AccountCustomerStatus.LOCKED) {
            throw new AppException(ErrorCode.USER_LOCKED);
        }
        Long customerId = (entity.getCustomer()!=null)?entity.getCustomer().getCustomerId():null;
        String fullName = (entity.getCustomer()!=null)?entity.getCustomer().getFullName(): entity.getUsername();
        String token = jwtService.generateToken(entity);

        return LoginResponseDTO.builder()
                .userId(entity.getUserId())
                .username(entity.getUsername())
                .role(entity.getRole())
                .customerId(customerId)
                .fullName(fullName)
                .authenticated(true)
                .token(token)
                .tokenType("Bearer")
                .message("Đăng nhập thành công.")
                .build();
    }
    @Transactional
    public LoginResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        CustomerEntity customer = new CustomerEntity();
        customer.setFullName(request.getFullName());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setAddress(request.getAddress());
        customer.setCustomerStatus(AccountCustomerStatus.ACTIVE);
        CustomerEntity savedCustomer = customerRepository.save(customer);

        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CUSTOMER)
                .customer(savedCustomer)
                .build();
        UserEntity savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);

        return LoginResponseDTO.builder()
                .userId(savedUser.getUserId())
                .username(savedUser.getUsername())
                .role(savedUser.getRole())
                .customerId(savedCustomer.getCustomerId())
                .fullName(savedCustomer.getFullName())
                .authenticated(true)
                .token(token)
                .tokenType("Bearer")
                .message("Đăng ký tài khoản thành công.")
                .build();
    }
}
