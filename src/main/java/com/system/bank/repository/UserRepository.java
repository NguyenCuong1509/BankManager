package com.system.bank.repository;

import com.system.bank.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = """
    SELECT *
    FROM users
    WHERE username = :username
    """, nativeQuery = true)    Optional<UserEntity> findByUsername(@Param("username")String username);
    boolean existsByUsername(String username);
}