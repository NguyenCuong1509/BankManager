package com.system.bank.controller;


import com.system.bank.dto.AccountReponseDTO;
import com.system.bank.dto.ApiReponse;
import com.system.bank.enums.AccountCustomerStatus;
import com.system.bank.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final CustomerService customerService;

    public AccountController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<ApiReponse<AccountReponseDTO>> getAccountById(@PathVariable Long accountId) {
        AccountReponseDTO account = customerService.findByAccountId(accountId);
        return ResponseEntity.ok(ApiReponse.<AccountReponseDTO>builder()
                .code(1000)
                .message("Lấy thông tin tài khoản thành công.")
                .result(account)
                .build());
    }

    @PatchMapping("/{accountId}/status")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiReponse<AccountReponseDTO>> updateAccountStatus(@PathVariable Long accountId,
                                                                             @RequestParam AccountCustomerStatus status) {
        AccountReponseDTO account = customerService.updateAccountStatus(accountId, status);
        return ResponseEntity.ok(ApiReponse.<AccountReponseDTO>builder()
                .code(1000)
                .message("Cập nhật trạng thái tài khoản thành công.")
                .result(account)
                .build());
    }
}
