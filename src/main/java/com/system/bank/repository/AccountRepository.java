package com.system.bank.repository;

import com.system.bank.entity.AccountEntity;
import com.system.bank.enums.AccountCustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    List<AccountEntity> findByCustomer_CustomerId(Long customerId);
    Optional<AccountEntity> findByAccountNumber(String accountNumber);
    List<AccountEntity> findByAccountStatus(AccountCustomerStatus accountStatus);
    boolean existsByAccountNumber(String accountNumber);
}
