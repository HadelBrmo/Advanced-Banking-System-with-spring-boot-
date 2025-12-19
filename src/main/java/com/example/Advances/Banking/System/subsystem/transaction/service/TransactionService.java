package com.example.Advances.Banking.System.subsystem.transaction.service;

import com.example.Advances.Banking.System.core.enums.AccountStatus;
import com.example.Advances.Banking.System.core.enums.TransactionType;
import com.example.Advances.Banking.System.core.model.*;
import com.example.Advances.Banking.System.subsystem.transaction.TransferRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class TransactionService {
    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    public Transaction processTransfer(TransferRequest request) {
        log.info("💰 Processing transfer from {} to {}, Amount: ${}",
                request.getFromAccount(), request.getToAccount(), request.getAmount());

        // 1. التحقق الأساسي
        validateBasicRequirements(request);

        try {
            // 2. إنشاء Accounts مؤقتة
            Account fromAccount = createTempAccount(request.getFromAccount());
            Account toAccount = createTempAccount(request.getToAccount());

            Transaction transaction = new Transaction(
                    TransactionType.TRANSFER,
                    request.getAmount(),
                    fromAccount,
                    toAccount
            );

            // 4. ⭐ Async processing
            processPostTransferTasksAsync(transaction);

            log.info("✅ Transfer processed successfully");
            return transaction;

        } catch (Exception e) {
            log.error("❌ Failed to process transfer", e);
            throw new RuntimeException("Transfer failed: " + e.getMessage(), e);
        }
    }

    // إنشاء Account مؤقت من رقم الحساب
    private Account createTempAccount(String accountNumber) {
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setBalance(5000.0);  // رصيد افتراضي
        account.setStatus(AccountStatus.ACTIVE);
        return account;
    }

    private void validateBasicRequirements(TransferRequest request) {
        if (request.getFromAccount() == null || request.getFromAccount().isEmpty()) {
            throw new IllegalArgumentException("From account is required");
        }
        if (request.getToAccount() == null || request.getToAccount().isEmpty()) {
            throw new IllegalArgumentException("To account is required");
        }
        if (request.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    @Async
    public void processPostTransferTasksAsync(Transaction transaction) {
        log.info("🔄 Starting async processing for transaction");

        try {
            // محاكاة مهام async
            Thread.sleep(1500);
            log.debug("   Task 1: Validation completed");

            Thread.sleep(1000);
            log.debug("   Task 2: Notifications sent");

            Thread.sleep(800);
            log.debug("   Task 3: Reports updated");

            log.info("✅ Async processing completed");

        } catch (InterruptedException e) {
            log.error("Async processing interrupted", e);
            Thread.currentThread().interrupt();
        }
    }
}