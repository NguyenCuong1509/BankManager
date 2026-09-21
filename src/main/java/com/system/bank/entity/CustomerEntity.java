package com.system.bank.entity;

import com.system.bank.enums.AccountCustomerStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerId")
    Long customerId;

    @Column(name = "FullName")
    String fullName;

    @Column(name = "PhoneNumber")
    String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "CustomerStatus")
    AccountCustomerStatus customerStatus;

    @Column(name = "DateOfBirth")
    LocalDate dateOfBirth;

    @Column(name = "Address")
    String address;

    @OneToMany(mappedBy = "customer")
    List<AccountEntity> account;


}
