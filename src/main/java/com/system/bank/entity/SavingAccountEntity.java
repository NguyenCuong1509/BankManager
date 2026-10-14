package com.system.bank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "SAVING_ACCOUNT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SavingAccountEntity extends AccountEntity {
    @Column(name = "INTEREST_RATE")
    BigDecimal interestRate;

    @Column(name = "TERM_MONTH")
    int termMonths;

    @Column(name = "MATURITY_DATE")
    LocalDate maturityDate;
}
