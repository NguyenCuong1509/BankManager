package com.system.bank.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "AUDIT_LOG")
@Getter
@Setter
public class AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "AUDIT_ID")
    private Long auditId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ACTION")
    private String action;

    @Column(name = "ENTITY_TYPE")
    private String entityType;

    @Column(name = "ENTITY_ID")
    private String entityId;

    @Lob
    @Column(name = "OLD_VALUE")
    private String oldValue;

    @Lob
    @Column(name = "NEW_VALUE")
    private String newValue;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "CHECK_SUM")
    private String checkSum;
}