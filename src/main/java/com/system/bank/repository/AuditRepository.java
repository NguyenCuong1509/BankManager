package com.system.bank.repository;

import com.system.bank.entity.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<AuditEntity,Long> {
}
