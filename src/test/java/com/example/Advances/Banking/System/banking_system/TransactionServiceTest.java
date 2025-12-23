package com.example.Advances.Banking.System.banking_system;

import com.example.Advances.Banking.System.core.model.Transaction;
import com.example.Advances.Banking.System.subsystem.transaction.TransferRequest;
import com.example.Advances.Banking.System.subsystem.transaction.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionServiceTest {

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService();
    }

    @Test
    @DisplayName("✅ اختبار إيداع ناجح")
    void testSuccessfulDeposit() {
        String accountNumber = "ACC12345678";
        double amount = 1000.0;

        Transaction transaction = transactionService.processDeposit(accountNumber, amount);

        assertNotNull(transaction);
        assertEquals("DEPOSIT", transaction.getType().name());
        assertEquals(amount, transaction.getAmount(), 0.001);
        assertEquals("COMPLETED", transaction.getStatus());

        System.out.println("✅ Deposit test passed: " + transaction.getTransactionId());
    }

    @Test
    @DisplayName("❌ اختبار إيداع بمبلغ غير صالح")
    void testInvalidDepositAmount() {
        String accountNumber = "ACC12345678";
        double invalidAmount = -100.0;

        Exception exception = assertThrows(RuntimeException.class, () -> {
            transactionService.processDeposit(accountNumber, invalidAmount);
        });

        assertTrue(exception.getMessage().contains("must be positive"));
        System.out.println("✅ Invalid deposit test passed");
    }

    @Test
    @DisplayName("✅ اختبار سحب ناجح")
    void testSuccessfulWithdrawal() {
        String accountNumber = "ACC12345678";
        double amount = 500.0;

        Transaction transaction = transactionService.processWithdrawal(accountNumber, amount);

        assertNotNull(transaction);
        assertEquals("WITHDRAWAL", transaction.getType().name());
        assertEquals(amount, transaction.getAmount(), 0.001);
        assertEquals("COMPLETED", transaction.getStatus());

        System.out.println("✅ Withdrawal test passed: " + transaction.getTransactionId());
    }

    @Test
    @DisplayName("✅ اختبار تحويل ناجح")
    void testSuccessfulTransfer() {
        TransferRequest request = new TransferRequest("ACC12345678", "ACC87654321", 300.0);
        request.setDescription("Test transfer");

        Transaction transaction = TransactionService.processTransfer(request);

        assertNotNull(transaction);
        assertEquals("TRANSFER", transaction.getType().name());
        assertEquals(300.0, transaction.getAmount(), 0.001);

        System.out.println("✅ Transfer test passed: " + transaction.getTransactionId());
    }

    @Test
    @DisplayName("📊 اختبار أداء متعدد المعاملات")
    void testMultipleTransactionsPerformance() {
        long startTime = System.currentTimeMillis();

        // محاكاة 10 معاملات
        for (int i = 1; i <= 10; i++) {

            String accountNumber = String.format("ACC%08d", i); // ينتج ACC00000001            transactionService.processDeposit(accountNumber, 100.0 * i);
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("⏱️  10 transactions completed in " + duration + "ms");
        assertTrue(duration < 5000, "Should complete within 5 seconds");
    }
}
