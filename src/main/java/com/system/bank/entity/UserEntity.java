package com.system.bank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "users")
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
    @Column(name = "UserId")
    Long userId;

    @NotBlank(message = "Username không được để trống")
    @Column(name = "Username", unique = true, nullable = false)
    String username;

    @NotBlank(message = "Password không được để trống")
    @Column(name = "Password", nullable = false)
    String password;

    @JoinColumn(name = "RoleId")
    @ManyToOne
    RoleEntity role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerId")
    CustomerEntity customer;
}
