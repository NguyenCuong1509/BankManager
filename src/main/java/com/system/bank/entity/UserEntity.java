package com.system.bank.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    Long userId;

    @Column(name = "Username", unique = true, nullable = false)
    String username;

    @Column(name = "Password", nullable = false)
    String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "Role")
    Role role;

    @OneToOne
    @JoinColumn(name = "CustomerId")
    CustomerEntity customer;
}
