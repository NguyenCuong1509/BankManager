package com.system.bank.service;

import com.system.bank.dto.TransactionResponseDTO;
import com.system.bank.entity.AccountEntity;
import com.system.bank.entity.TransactionEntity;
import com.system.bank.enums.AccountCustomerStatus;
import com.system.bank.enums.TransactionStatus;
import com.system.bank.enums.TransactionType;
import com.system.bank.exception.AppException;
import com.system.bank.exception.ErrorCode;
import com.system.bank.mapper.TransactionMapper;
import com.system.bank.repository.AccountRepository;
import com.system.bank.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TranferService {
}
