package com.system.bank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "USER-SEQ", allocationSize = 1)
    @Column(name = "USER_ID")
    Long userId;

    @NotBlank(message = "Username không được để trống")
    @Column(name = "USERNAME", unique = true, nullable = false)
    String username;

    @NotBlank(message = "Password không được để trống")
    @Column(name = "PASSWORD", nullable = false)
    String password;

    @Column(name = "ROLE_ID")
    Long roleId;

    @Column(name = "CUSTOMER_ID")
    Long customerId;
}
