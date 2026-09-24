package com.system.bank.dto;

import com.system.bank.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private Long userId;
    private String username;
    private Role role;
    private Long customerId;
    private String fullName;
    private boolean authenticated;
    private String token;
    private String tokenType;
    private String message;
}

