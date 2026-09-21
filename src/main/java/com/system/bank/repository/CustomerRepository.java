package com.system.bank.repository;

import com.system.bank.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {
        Optional<CustomerEntity> findByCustomerId(Long CustomerId);
}
