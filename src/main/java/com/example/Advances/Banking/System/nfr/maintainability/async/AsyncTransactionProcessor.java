package com.example.Advances.Banking.System.nfr.maintainability.async;

import com.example.Advances.Banking.System.core.model.Transaction;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;  // ⭐ استخدم هذا بدل @Slf4j

@Service
// ❌ أزل @Slf4j
public class AsyncTransactionProcessor {

    // ⭐ أضف Logger يدوياً
    private static final Logger log = LoggerFactory.getLogger(AsyncTransactionProcessor.class);

    @Async
    public void processTransactionAsync(Transaction transaction) {
        if (transaction == null) {
            log.error("❌ Cannot process null transaction");
            return;
        }

        String transactionId = (transaction.getId() != null) ?
                transaction.getId().toString() : "UNKNOWN";

        log.info("🔄 Starting ASYNC processing for transaction: {}", transactionId);

        try {
            performAdvancedValidation(transaction);

            updateExternalSystems(transaction);

            sendNotifications(transaction);

            updateReports(transaction);

            log.info("✅ ASYNC processing completed for transaction: {}", transactionId);

        } catch (Exception e) {
            log.error("❌ Error in async processing for transaction: {}", transactionId, e);
            handleAsyncError(transaction, e);
        }
    }

    private void performAdvancedValidation(Transaction transaction) {
        String transactionId = (transaction.getId() != null) ?
                transaction.getId().toString() : "UNKNOWN";

        log.debug("Validating transaction {}", transactionId);
        try {
            Thread.sleep(1000);
            log.debug("Validation completed for {}", transactionId);
        } catch (InterruptedException e) {
            log.warn("Validation interrupted for transaction: {}", transactionId);
            Thread.currentThread().interrupt();
        }
    }

    private void updateExternalSystems(Transaction transaction) {
        String transactionId = (transaction.getId() != null) ?
                transaction.getId().toString() : "UNKNOWN";

        log.debug("Updating external systems for {}", transactionId);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void sendNotifications(Transaction transaction) {
        String transactionId = (transaction.getId() != null) ?
                transaction.getId().toString() : "UNKNOWN";

        log.debug("Sending notifications for {}", transactionId);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void updateReports(Transaction transaction) {
        String transactionId = (transaction.getId() != null) ?
                transaction.getId().toString() : "UNKNOWN";

        log.debug("Updating reports for {}", transactionId);
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void handleAsyncError(Transaction transaction, Exception e) {
        String transactionId = (transaction != null && transaction.getId() != null) ?
                transaction.getId().toString() : "UNKNOWN";

        log.error("Async error handler for transaction: {}", transactionId);
        // يمكنك هنا:
        // 1. إرسال email للدعم
        // 2. تسجيل في database
        // 3. إعادة المحاولة
    }

    /**
     * ⭐ Method إضافي للاختبار
     */
    @Async
    public void simpleAsyncTask(String taskName) {
        log.info("Starting async task: {}", taskName);
        try {
            Thread.sleep(2000);
            log.info("Completed async task: {}", taskName);
        } catch (InterruptedException e) {
            log.error("Task {} interrupted", taskName, e);
            Thread.currentThread().interrupt();
        }
    }
}