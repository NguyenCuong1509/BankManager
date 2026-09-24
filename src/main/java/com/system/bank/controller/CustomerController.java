package com.system.bank.controller;

import com.system.bank.dto.AccountReponseDTO;
import com.system.bank.dto.AccountRequestDTO;
import com.system.bank.dto.ApiReponse;
import com.system.bank.dto.CustomerReponseDTO;
import com.system.bank.dto.CustomerRequestDTO;
import com.system.bank.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiReponse<List<CustomerReponseDTO>>> getAllCustomer() {
        List<CustomerReponseDTO> customers = customerService.getAll();
        return ResponseEntity.ok(ApiReponse.<List<CustomerReponseDTO>>builder()
                .code(1000)
                .message("Lấy danh sách khách hàng thành công.")
                .result(customers)
                .build());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<ApiReponse<CustomerReponseDTO>> getCustomerById(@PathVariable Long customerId) {
        CustomerReponseDTO customer = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(ApiReponse.<CustomerReponseDTO>builder()
                .code(1000)
                .message("Lấy thông tin khách hàng thành công.")
                .result(customer)
                .build());
    }

    @PostMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiReponse<CustomerReponseDTO>> createCustomer(@RequestBody @Valid CustomerRequestDTO requestDTO) {
        CustomerReponseDTO created = customerService.create(requestDTO);
        return ResponseEntity.ok(ApiReponse.<CustomerReponseDTO>builder()
                .code(1000)
                .message("Tạo mới khách hàng thành công.")
                .result(created)
                .build());
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<ApiReponse<CustomerReponseDTO>> updateCustomer(@PathVariable Long customerId,
                                                                         @RequestBody @Valid CustomerRequestDTO requestDTO) {
        CustomerReponseDTO updated = customerService.update(customerId, requestDTO);
        return ResponseEntity.ok(ApiReponse.<CustomerReponseDTO>builder()
                .code(1000)
                .message("Cập nhật thông tin khách hàng thành công.")
                .result(updated)
                .build());
    }

    @DeleteMapping("/{customerId}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiReponse<String>> lockCustomer(@PathVariable Long customerId) {
        customerService.lock(customerId);
        return ResponseEntity.ok(ApiReponse.<String>builder()
                .code(1000)
                .message("Khóa tài khoản khách hàng thành công.")
                .result("Khách hàng đã được chuyển sang trạng thái LOCKED")
                .build());
    }

    @PostMapping("/accounts")
    public ResponseEntity<ApiReponse<AccountReponseDTO>> createAccount(@RequestBody @Valid AccountRequestDTO requestDTO) {
        AccountReponseDTO account = customerService.createAccount(requestDTO);
        return ResponseEntity.ok(ApiReponse.<AccountReponseDTO>builder()
                .code(1000)
                .message("Mở tài khoản ngân hàng thành công.")
                .result(account)
                .build());
    }

    @GetMapping("/{customerId}/accounts")
    public ResponseEntity<ApiReponse<List<AccountReponseDTO>>> getAccountsByCustomerId(@PathVariable Long customerId) {
        List<AccountReponseDTO> accounts = customerService.getAllAccount(customerId);
        return ResponseEntity.ok(ApiReponse.<List<AccountReponseDTO>>builder()
                .code(1000)
                .message("Lấy danh sách tài khoản của khách hàng thành công.")
                .result(accounts)
                .build());
    }
}
