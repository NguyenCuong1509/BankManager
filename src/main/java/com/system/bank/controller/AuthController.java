package com.system.bank.controller;


import com.system.bank.dto.ApiReponse;
import com.system.bank.dto.LoginRequestDTO;
import com.system.bank.dto.LoginResponseDTO;
import com.system.bank.dto.RegisterRequestDTO;
import com.system.bank.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiReponse<LoginResponseDTO>> login(@RequestBody @Valid LoginRequestDTO request){
        LoginResponseDTO response = authService.login(request);
        return ResponseEntity.ok(ApiReponse.<LoginResponseDTO>builder()
                .code(1000)
                .message("Đăng nhập thành công.")
                .result(response)
                .build());
    }

    @PostMapping("/register")
    public ResponseEntity<ApiReponse<LoginResponseDTO>> register(@RequestBody @Valid RegisterRequestDTO request) {
        LoginResponseDTO response = authService.register(request);
        return ResponseEntity.ok(ApiReponse.<LoginResponseDTO>builder()
                .code(1000)
                .message("Đăng ký thành công.")
                .result(response)
                .build());
    }
}
