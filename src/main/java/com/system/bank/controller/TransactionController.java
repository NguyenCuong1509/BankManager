package com.system.bank.controller;

import com.system.bank.dto.*;
import com.system.bank.service.TranferService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TranferService tranferService;

    public TransactionController(TranferService tranferService) {
        this.tranferService = tranferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiReponse<TransactionResponseDTO>> transfer(@RequestBody @Valid TransferRequestDTO request) {
        TransactionResponseDTO response = tranferService.transfer(request);
        return ResponseEntity.ok(ApiReponse.<TransactionResponseDTO>builder()
                .code(1000)
                .message("Chuyển tiền thành công.")
                .result(response)
                .build());
    }

    @PostMapping("/deposit")
    public ResponseEntity<ApiReponse<TransactionResponseDTO>> deposit(@RequestBody @Valid DepositRequestDTO request) {
        TransactionResponseDTO response = tranferService.deposit(request);
        return ResponseEntity.ok(ApiReponse.<TransactionResponseDTO>builder()
                .code(1000)
                .message("Nạp tiền vào tài khoản thành công.")
                .result(response)
                .build());
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ApiReponse<TransactionResponseDTO>> withdraw(@RequestBody @Valid WithdrawRequestDTO request) {
        TransactionResponseDTO response = tranferService.withdraw(request);
        return ResponseEntity.ok(ApiReponse.<TransactionResponseDTO>builder()
                .code(1000)
                .message("Rút tiền từ tài khoản thành công.")
                .result(response)
                .build());
    }

    @GetMapping("/history/{accountNumber}")
    public ResponseEntity<ApiReponse<List<TransactionResponseDTO>>> getHistory(@PathVariable String accountNumber) {
        List<TransactionResponseDTO> history = tranferService.getTransactionHistory(accountNumber);
        return ResponseEntity.ok(ApiReponse.<List<TransactionResponseDTO>>builder()
                .code(1000)
                .message("Lấy lịch sử giao dịch thành công.")
                .result(history)
                .build());
    }

    @GetMapping("/{refNo}")
    public ResponseEntity<ApiReponse<TransactionResponseDTO>> getByRefNo(@PathVariable String refNo) {
        TransactionResponseDTO transaction = tranferService.getbyRefNo(refNo);
        return ResponseEntity.ok(ApiReponse.<TransactionResponseDTO>builder()
                .code(1000)
                .message("Lấy chi tiết giao dịch thành công.")
                .result(transaction)
                .build());
    }
}
