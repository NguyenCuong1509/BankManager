package com.system.bank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "payment_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentAccountEntity extends AccountEntity {
    @Column(name = "DailyTransferred")
    BigDecimal dailyTransferred;

    @Column(name = "TransferCountToday")
    int transferCountToday;
}
