package com.system.bank.service;

import com.system.bank.entity.AuditEntity;
import com.system.bank.repository.AuditRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Slf4j
public class AuditService {
    private final AuditRepository auditRepository;
    private final CheckSumService checkSumService;
    private final ObjectMapper objectMapper;


    public AuditService(AuthService authService, AuditRepository auditRepository, CheckSumService checkSumService, ObjectMapper objectMapper) {
        this.auditRepository = auditRepository;
        this.checkSumService = checkSumService;
        this.objectMapper = objectMapper;
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createAudit(
            String userId,
            String action,
            String entityType,
            String entityId,
            String oldValue,
            String newValue,
            String status
    ) {
        AuditEntity audit = new AuditEntity();
        audit.setUserId(userId);
        audit.setAction(action);
        audit.setEntityType(entityType);
        audit.setEntityId(entityId);
        audit.setOldValue(oldValue);
        audit.setNewValue(newValue);
        audit.setStatus(status);
        audit.setCreatedAt(Instant.now());
        try {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("userId", userId);
            data.put("action", action);
            data.put("entityType", entityType);
            data.put("entityId", entityId);
            data.put("oldValue", oldValue);
            data.put("newValue", newValue);
            data.put("status", status);
            String json = objectMapper.writeValueAsString(data);
            String checkSum = checkSumService.genCheccksum(json);
            audit.setCheckSum(checkSum);
        } catch (Exception e) {
            // Không throw để tránh làm fail cả luồng audit
            // Vẫn lưu audit nhưng không có checksum
            log.error("Không thể tạo checksum cho audit: userId={}, action={}, entityId={}", userId, action, entityId, e);
        }
        auditRepository.save(audit);
        log.debug("Audit lưu thành công: action={}, entityType={}, entityId={}, status={}", action, entityType, entityId, status);
    }
}