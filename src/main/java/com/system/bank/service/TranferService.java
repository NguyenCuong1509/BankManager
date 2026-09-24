package com.system.bank.service;

import com.system.bank.dto.DepositRequestDTO;
import com.system.bank.dto.TransactionResponseDTO;
import com.system.bank.dto.TransferRequestDTO;
import com.system.bank.dto.WithdrawRequestDTO;
import com.system.bank.entity.AccountEntity;
import com.system.bank.entity.TransactionEntity;
import com.system.bank.enums.AccountCustomerStatus;
import com.system.bank.enums.TransactionStatus;
import com.system.bank.enums.TransactionType;
import com.system.bank.exception.AppException;
import com.system.bank.exception.ErrorCode;
import com.system.bank.mapper.TransactionMapper;
import com.system.bank.repository.AccountRepository;
import com.system.bank.repository.CustomerRepository;
import com.system.bank.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.midi.MidiFileFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TranferService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TranferService(AccountRepository accountRepository, TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public TransactionResponseDTO transfer(TransferRequestDTO request) {
        if (request.getFormAccount().equalsIgnoreCase(request.getToAccount())) {
            throw new AppException(ErrorCode.SAME_ACCOUNT_TRANSFER);
        }

        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new AppException(ErrorCode.RESULT_INVALID);
        }

        AccountEntity fromAccount = accountRepository.findByAccountNumber(request.getFormAccount())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        AccountEntity toAccount = accountRepository.findByAccountNumber(request.getToAccount())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        if (fromAccount.getAccountStatus() == AccountCustomerStatus.LOCKED ||
                toAccount.getAccountStatus() == AccountCustomerStatus.LOCKED) {
            throw new AppException(ErrorCode.ACCOUNT_LOCKED);
        }

        if (fromAccount.getDailyLimit() != null && request.getAmount().compareTo(fromAccount.getDailyLimit()) > 0) {
            throw new AppException(ErrorCode.DAILY_LIMIT_EXCEEDED);
        }

        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new AppException(ErrorCode.INSUFFICIENT_BALANCE);
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));

        accountRepository.save(fromAccount);
        accountRepository.save((toAccount));

        String refNo = "TXN" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        TransactionEntity transaction = new TransactionEntity();
        transaction.setRefNo(refNo);
        transaction.setFromAccount(fromAccount.getAccountNumber());
        transaction.setToAccount(toAccount.getAccountNumber());
        transaction.setAmount(request.getAmount());
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setTranType(TransactionType.TRANSFER);
        transaction.setCreateAt(LocalDateTime.now());
        transaction.setAccount(fromAccount);

        TransactionEntity savedTxn = transactionRepository.save(transaction);
        log.info("Chuyển khoản thành công từ {} sang {} số tiền: {} (Mã GD: {})",
                request.getFormAccount(), request.getToAccount(), request.getAmount(), refNo);

        return transactionMapper.toResponseDto(savedTxn);
    }

    @Transactional(rollbackFor = Exception.class)
    public TransactionResponseDTO deposit(DepositRequestDTO request) {
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new AppException(ErrorCode.INVALID_AMOUNT);
        }

        AccountEntity account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        if (account.getAccountStatus() == AccountCustomerStatus.LOCKED) {
            throw new AppException(ErrorCode.ACCOUNT_LOCKED);
        }
        account.setBalance(account.getBalance().add(request.getAmount()));

        accountRepository.save(account);

        String refNo = "DEP" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        TransactionEntity transaction = new TransactionEntity();
        transaction.setRefNo(refNo);
        transaction.setFromAccount("CASH_DEPOSIT");
        transaction.setToAccount(account.getAccountNumber());
        transaction.setAmount(request.getAmount());
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setTranType(TransactionType.DEPOSIT);
        transaction.setCreateAt(LocalDateTime.now());
        transaction.setAccount(account);

        TransactionEntity savedTxn = transactionRepository.save(transaction);
        log.info("Nạp tiền thành công vào tài khoản {} số tiền: {} (Mã GD: {})",
                request.getAccountNumber(), request.getAmount(), refNo);

        return transactionMapper.toResponseDto(savedTxn);
    }

    @Transactional(rollbackFor = Exception.class)
    public TransactionResponseDTO withdraw(WithdrawRequestDTO request){
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new AppException(ErrorCode.INVALID_AMOUNT);
        }

        AccountEntity account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        if (account.getAccountStatus() == AccountCustomerStatus.LOCKED) {
            throw new AppException(ErrorCode.ACCOUNT_LOCKED);
        }

        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new AppException(ErrorCode.INSUFFICIENT_BALANCE);
        }

        account.setBalance(account.getBalance().subtract(request.getAmount()));
        accountRepository.save(account);

        String refNo = "WTH" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        TransactionEntity transaction = new TransactionEntity();
        transaction.setRefNo(refNo);
        transaction.setFromAccount(account.getAccountNumber());
        transaction.setToAccount("CASH_WITHDRAW");
        transaction.setAmount(request.getAmount());
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setTranType(TransactionType.WITHDRAW);
        transaction.setCreateAt(LocalDateTime.now());
        transaction.setAccount(account);

        TransactionEntity savedTxn = transactionRepository.save(transaction);
        log.info("Rút tiền thành công từ tài khoản {} số tiền: {} (Mã GD: {})",
                request.getAccountNumber(), request.getAmount(), refNo);

        return transactionMapper.toResponseDto(savedTxn);
    }

   @Transactional(readOnly = true)
    public List<TransactionResponseDTO> getTransactionHistory(String accountNumber){
        if (!accountRepository.existsByAccountNumber(accountNumber)){
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        return transactionRepository.findHistoryByAccountNumber(accountNumber).stream()
                .map(transactionMapper::toResponseDto)
                .collect(Collectors.toList());
   }

   @Transactional(readOnly = true)
    public TransactionResponseDTO getbyRefNo(String refNo){
        TransactionEntity entity = transactionRepository.findByRefNo(refNo)
                .orElseThrow(()->new AppException(ErrorCode.TRANSACTION_NOT_FOUND));
        return transactionMapper.toResponseDto(entity);

   }
}