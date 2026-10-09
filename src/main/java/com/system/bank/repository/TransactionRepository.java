package com.system.bank.repository;

import com.system.bank.entity.TransactionEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    Optional<TransactionEntity> findByRefNo(String refNo);

    @Query("SELECT t FROM TransactionEntity t WHERE t.fromAccount = :accountNumber OR t.toAccount = :accountNumber ORDER BY t.createAt DESC")
    List<TransactionEntity> findHistoryByAccountNumber(@Param("accountNumber") String accountNumber);

    @Query("SELECT t FROM TransactionEntity t WHERE t.fromAccount = :accountNumber OR t.toAccount = :accountNumber ORDER BY t.createAt DESC")
    Page<TransactionEntity> findHistoryByAccountNumber(@Param("accountNumber") String accountNumber, Pageable pageable);
}