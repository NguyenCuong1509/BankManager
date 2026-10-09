package com.system.bank.repository;

import com.system.bank.dto.CustomerRequestDTO;
import com.system.bank.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {
    Optional<CustomerEntity> findByPhoneNumber(String phoneNumber);
    @Query(value = """
SELECT DISTINCT c FROM CustomerEntity c LEFT JOIN FETCH c.account
""")
    List<CustomerEntity> findAllWithAccount();
}
