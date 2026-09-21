package com.system.bank.mapper;

import com.system.bank.dto.TransactionResponseDTO;
import com.system.bank.entity.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionResponseDTO toResponseDto(TransactionEntity entity) {
        if (entity == null) return null;
        return TransactionResponseDTO.builder()
                .transactionId(entity.getTransactionId())
                .refNo(entity.getRefNo())
                .fromAccount(entity.getFromAccount())
                .toAccount(entity.getToAccount())
                .amount(entity.getAmount())
                .status(entity.getStatus())
                .tranType(entity.getTranType())
                .createAt(entity.getCreateAt())
                .failureReason(entity.getFailureReason())
                .build();
    }
}
