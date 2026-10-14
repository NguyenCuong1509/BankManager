package com.system.bank.entity;

import com.system.bank.enums.AccountCustomerStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "CUSTOMER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(name = "customer_seq", sequenceName = "CUSTOMER_SEQ",allocationSize = 1)
    @Column(name = "CUSTOMER_ID")
    Long customerId;

    @Column(name = "FULL_NAME")
    String fullName;

    @Column(name = "PHONE_NUMBER")
    String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "CUSTOMER_STATUS")
    AccountCustomerStatus customerStatus;

    @Column(name = "DATE_OF_BIRTH")
    LocalDate dateOfBirth;

    @Column(name = "ADDRESS")
    String address;

    @Column(name = "ACCOUNT_ID")
    Long accountId;


}
