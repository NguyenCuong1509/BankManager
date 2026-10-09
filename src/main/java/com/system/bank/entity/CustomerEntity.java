package com.system.bank.entity;

import com.system.bank.enums.AccountCustomerStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(name = "customer_seq", sequenceName = "CUSTOMER_SEQ",allocationSize = 1)
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
