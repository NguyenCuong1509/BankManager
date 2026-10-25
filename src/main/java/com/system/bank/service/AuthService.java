package com.system.bank.service;

import com.system.bank.dto.LoginRequestDTO;
import com.system.bank.dto.LoginResponseDTO;
import com.system.bank.dto.RegisterRequestDTO;
import com.system.bank.entity.CustomerEntity;
import com.system.bank.entity.RoleEntity;
import com.system.bank.entity.UserEntity;
import com.system.bank.enums.AccountCustomerStatus;
import com.system.bank.enums.Role;
import com.system.bank.exception.AppException;
import com.system.bank.exception.ErrorCode;
import com.system.bank.repository.CustomerRepository;
import com.system.bank.repository.RoleRepository;
import com.system.bank.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       CustomerRepository customerRepository,
                       PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
    }
    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO request) {

        UserEntity entity = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                entity.getPassword()
        );

        if (!matches) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        Long customerId = entity.getCustomerId();
        String fullName = entity.getUsername();
        String roleName;

        if (customerId != null) {
            CustomerEntity customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

            if (customer.getCustomerStatus() == AccountCustomerStatus.LOCKED) {
                throw new AppException(ErrorCode.USER_LOCKED);
            }

            fullName = customer.getFullName();
        }

        RoleEntity role = roleRepository.findById(entity.getRoleId())
                .orElseThrow(() -> new RuntimeException(new AppException(ErrorCode.ROLE_NOT_FOUND)));

        roleName = role.getNameRole();
        String token = jwtService.genToken(entity.getUsername());

        return LoginResponseDTO.builder()
                .userId(entity.getUserId())
                .username(entity.getUsername())
                .role(roleName)
                .token(token)
                .customerId(customerId)
                .fullName(fullName)
                .authenticated(true)
                .message("Đăng nhập thành công.")
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public LoginResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        RoleEntity role = roleRepository.findByNameRole(Role.CUSTOMER.getName()).orElseThrow(()->new AppException(ErrorCode.ROLE_NOT_FOUND));

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
                .customerId(savedCustomer.getCustomerId())
                .roleId(role.getRoleId())
                .build();
        UserEntity savedUser = userRepository.save(user);

        String token = jwtService.genToken(savedUser.getUsername());

        return LoginResponseDTO.builder()
                .userId(savedUser.getUserId())
                .username(savedUser.getUsername())
                .customerId(savedCustomer.getCustomerId())
                .fullName(savedCustomer.getFullName())
                .role(role.getNameRole())
                .token(token)
                .authenticated(true)
                .message("Đăng ký tài khoản thành công.")
                .build();
    }
}
