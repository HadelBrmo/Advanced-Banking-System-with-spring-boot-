package com.example.Advances.Banking.System.nfr.maintainability;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AuditLogger {

    public void logTransaction(String transactionId, String accountNumber,
                               String type, double amount, String status) {

        Map<String, Object> auditData = new HashMap<>();
        auditData.put("timestamp", LocalDateTime.now());
        auditData.put("transactionId", transactionId);
        auditData.put("accountNumber", accountNumber);
        auditData.put("type", type);
        auditData.put("amount", amount);
        auditData.put("status", status);

        log.info("🔍 AUDIT LOG - Transaction: {}", auditData);

    }

    public void logSecurityEvent(String event, String userId, String ipAddress) {
        log.warn(" SECURITY EVENT - {} by user {} from IP {}", event, userId, ipAddress);
    }

    public void logPerformance(String operation, long durationMs) {
        if (durationMs > 1000) {
            log.error(" SLOW OPERATION - {} took {}ms", operation, durationMs);
        } else if (durationMs > 500) {
            log.warn(" WARNING - {} took {}ms", operation, durationMs);
        } else {
            log.debug("⚡ {} completed in {}ms", operation, durationMs);
        }
    }
}