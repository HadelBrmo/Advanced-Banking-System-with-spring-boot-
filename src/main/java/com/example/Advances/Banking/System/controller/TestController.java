package com.example.Advances.Banking.System.controller;

import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;
import com.example.Advances.Banking.System.nfr.maintainability.AuditLogger;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final AuditLogger auditLogger;

    @GetMapping("/test")
    public Map<String, Object> testSystem() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "✅ النظام يعمل");
        response.put("timestamp", java.time.LocalDateTime.now());
        response.put("version", "1.0.0");
        response.put("features", new String[]{"Audit Logger", "System Monitoring", "Security", "Database Pooling"});

        // اختبار الـ Logging
        log.info("🔧 تم استدعاء /api/test - نظام الصيانة نشط");

        return response;
    }

    @PostMapping("/audit/test")
    public Map<String, String> testAuditLogger() {
        // اختبار AuditLogger
        auditLogger.logTransaction(
                "TXN-TEST-" + System.currentTimeMillis(),
                "ACC-TEST-001",
                "DEPOSIT",
                1000.0,
                "SUCCESS"
        );

        auditLogger.logSecurityEvent(
                "TEST_LOGIN_ATTEMPT",
                "testuser",
                "192.168.1.100"
        );

        auditLogger.logPerformance(
                "TEST_DATABASE_QUERY",
                850  // 850ms - يجب أن يظهر كـ WARNING
        );

        auditLogger.logPerformance(
                "TEST_FAST_OPERATION",
                150  // 150ms - يجب أن يظهر كـ DEBUG
        );

        auditLogger.logPerformance(
                "TEST_VERY_SLOW_OPERATION",
                1200  // 1200ms - يجب أن يظهر كـ ERROR
        );

        log.info("🧪 تم إجراء اختبار شامل لـ AuditLogger");

        Map<String, String> response = new HashMap<>();
        response.put("message", "✅ تم اختبار AuditLogger بنجاح");
        response.put("instruction", "تحقق من الـ Logs في الكونسول!");
        response.put("next_step", "انتظر 60 ثانية لرؤية SystemHealthMonitor يعمل");

        return response;
    }

    @GetMapping("/banking/test")
    public Map<String, Object> testBanking() {
        Customer customer = new Customer("Test", "User", "test@example.com");
        Account account = new Account(AccountType.SAVINGS, customer, 1000.0);

        // إجراء عمليات
        account.deposit(500.0);
        account.withdraw(200.0);

        Map<String, Object> response = new HashMap<>();
        response.put("accountNumber", account.getAccountNumber());
        response.put("balance", account.getBalance());
        response.put("customer", customer.getFullName());
        response.put("totalBalance", customer.getTotalBalance());
        response.put("transactions", new String[]{"Deposit $500", "Withdraw $200"});

        log.info("🏦 اختبار النظام البنكي: {}", response);

        return response;
    }

    @GetMapping("/health")
    public Map<String, Object> systemHealth() {
        Map<String, Object> response = new HashMap<>();
        response.put("service", "Advanced Banking System");
        response.put("status", "ACTIVE");
        response.put("timestamp", java.time.LocalDateTime.now());
        response.put("monitoring", "SystemHealthMonitor يعمل كل 60 ثانية");

        log.info("🏥 طلب حالة النظام - كل الأنظمة تعمل بشكل طبيعي");

        return response;
    }

    @PostConstruct
    public void init() {
        log.info("🚀 TestController جاهز للعمل!");
        log.info("================================================");
        log.info("📌 Endpoints المتاحة:");
        log.info("   GET  http://localhost:8080/api/test");
        log.info("   POST http://localhost:8080/api/audit/test");
        log.info("   GET  http://localhost:8080/api/banking/test");
        log.info("   GET  http://localhost:8080/api/health");
        log.info("================================================");
        log.info("⏱️  SystemHealthMonitor سيعمل بعد 60 ثانية...");
        log.info("🔍  AuditLogger جاهز لتسجيل جميع الأحداث");
        log.info("================================================");

        // اختبار أولي لـ AuditLogger
        auditLogger.logTransaction(
                "SYSTEM-INIT",
                "SYSTEM-ACCOUNT",
                "INITIALIZATION",
                0.0,
                "SYSTEM_STARTED"
        );
    }
}