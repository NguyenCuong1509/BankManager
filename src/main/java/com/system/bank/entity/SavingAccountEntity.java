package com.system.bank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "saving_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SavingAccountEntity extends AccountEntity {
    @Column(name = "InterestRate")
    BigDecimal interestRate;

    @Column(name = "TermMonths")
    int termMonths;

    @Column(name = "MaturityDate")
    LocalDate maturityDate;
}
