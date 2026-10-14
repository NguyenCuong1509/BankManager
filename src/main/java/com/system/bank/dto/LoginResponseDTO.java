package com.system.bank.dto;

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
    private Long customerId;
    private String fullName;
    private boolean authenticated;
    private String message;
    private String role;
}

