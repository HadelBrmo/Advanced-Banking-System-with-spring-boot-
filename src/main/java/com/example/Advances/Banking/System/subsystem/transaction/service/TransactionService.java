package com.example.Advances.Banking.System.subsystem.transaction.service;

import com.example.Advances.Banking.System.core.enums.AccountStatus;
import com.example.Advances.Banking.System.core.enums.TransactionType;
import com.example.Advances.Banking.System.core.model.*;
import com.example.Advances.Banking.System.subsystem.transaction.TransferRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Service
public class TransactionService {
    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    private static final double MIN_DEPOSIT_AMOUNT = 10.0;
    private static final double MAX_DEPOSIT_AMOUNT = 50000.0;
    private static final double MIN_WITHDRAWAL_AMOUNT = 10.0;
    private static final double DAILY_WITHDRAWAL_LIMIT = 5000.0;


    public void createMonthlyLoanPayment(String loanAccount, String customerAccount, double amount) {
        log.info("🏦 إنشاء دفعة قرض شهرية: {} → {} بمبلغ ${}",
                customerAccount, loanAccount, amount);

        if (!hasSufficientBalance(customerAccount, amount)) {
            log.error("❌ رصيد غير كافي لدفعة القرض");
            return;
        }

        TransferRequest request = new TransferRequest(customerAccount, loanAccount, amount);
        request.setDescription("Monthly Loan Payment - " + java.time.LocalDate.now().getMonth());

        Transaction transaction = processTransfer(request);

        sendPaymentNotification(customerAccount, amount, "loan");

        log.info("✅ دفعة القرض الشهرية مكتملة: {}", transaction.getTransactionId());
    }

    private boolean hasSufficientBalance(String accountNumber, double amount) {
        return true;
    }

    private void sendPaymentNotification(String account, double amount, String type) {
        log.info("📧 إشعار: تم خصم ${} من حساب {} لدفعة {}", amount, account, type);
    }

    public Transaction processDeposit(String accountNumber, double amount) {
        log.info("💰 Processing deposit to account: {}, Amount: ${}",
                accountNumber, amount);

        try {
            validateDepositRequest(accountNumber, amount);

            validateDepositLimits(amount);

            Account toAccount = createTempAccount(accountNumber);

            double newBalance = toAccount.getBalance() + amount;
            toAccount.setBalance(newBalance);

            Transaction transaction = Transaction.builder()
                    .transactionId(generateTransactionId())
                    .type(TransactionType.DEPOSIT)
                    .amount(amount)
                    .toAccount(toAccount)
                    .description("Deposit to account: " + accountNumber)
                    .status("COMPLETED")
                    .build();

            processPostDepositTasksAsync(transaction);

            log.info("✅ Deposit completed successfully. New balance: ${}", newBalance);
            return transaction;

        } catch (Exception e) {
            log.error("❌ Deposit failed for account: {}", accountNumber, e);
            throw new RuntimeException("Deposit failed: " + e.getMessage(), e);
        }
    }

    public Transaction processWithdrawal(String accountNumber, double amount) {
        log.info("💰 Processing withdrawal from account: {}, Amount: ${}",
                accountNumber, amount);

        try {
            validateWithdrawalRequest(accountNumber, amount);

            Account fromAccount = createTempAccount(accountNumber);

            validateSufficientBalance(fromAccount, amount);

            validateDailyWithdrawalLimit(accountNumber, amount);

            double newBalance = fromAccount.getBalance() - amount;
            fromAccount.setBalance(newBalance);

            Transaction transaction = Transaction.builder()
                    .transactionId(generateTransactionId())
                    .type(TransactionType.WITHDRAWAL)
                    .amount(amount)
                    .fromAccount(fromAccount)
                    .description("Withdrawal from account: " + accountNumber)
                    .status("COMPLETED")
                    .build();

            processPostWithdrawalTasksAsync(transaction);

            log.info("✅ Withdrawal completed successfully. New balance: ${}", newBalance);
            return transaction;

        } catch (Exception e) {
            log.error("❌ Withdrawal failed for account: {}", accountNumber, e);
            throw new RuntimeException("Withdrawal failed: " + e.getMessage(), e);
        }
    }


    public static Transaction processTransfer(TransferRequest request) {
        log.info("💰 Processing transfer from {} to {}, Amount: ${}",
                request.getFromAccount(), request.getToAccount(), request.getAmount());

        validateBasicRequirements(request);

        try {
            Account fromAccount = createTempAccount(request.getFromAccount());
            Account toAccount = createTempAccount(request.getToAccount());

            validateSufficientBalance(fromAccount, request.getAmount());

            fromAccount.setBalance(fromAccount.getBalance() - request.getAmount());
            toAccount.setBalance(toAccount.getBalance() + request.getAmount());

            Transaction transaction = Transaction.builder()
                    .transactionId(generateTransactionId())
                    .type(TransactionType.TRANSFER)
                    .amount(request.getAmount())
                    .fromAccount(fromAccount)
                    .toAccount(toAccount)
                    .description(request.getDescription())
                    .status("COMPLETED")
                    .build();

            processPostTransferTasksAsync(transaction);

            log.info("✅ Transfer processed successfully");
            return transaction;

        } catch (Exception e) {
            log.error("❌ Failed to process transfer", e);
            throw new RuntimeException("Transfer failed: " + e.getMessage(), e);
        }
    }



    private void validateDepositRequest(String accountNumber, double amount) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number is required");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        if (!isValidAccountNumber(accountNumber)) {
            throw new IllegalArgumentException("Invalid account number format");
        }
    }


    private void validateDepositLimits(double amount) {
        if (amount < MIN_DEPOSIT_AMOUNT) {
            throw new IllegalArgumentException(
                    String.format("Minimum deposit amount is $%.2f", MIN_DEPOSIT_AMOUNT)
            );
        }
        if (amount > MAX_DEPOSIT_AMOUNT) {
            throw new IllegalArgumentException(
                    String.format("Maximum deposit amount is $%.2f", MAX_DEPOSIT_AMOUNT)
            );
        }
    }


    private void validateWithdrawalRequest(String accountNumber, double amount) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number is required");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount < MIN_WITHDRAWAL_AMOUNT) {
            throw new IllegalArgumentException(
                    String.format("Minimum withdrawal amount is $%.2f", MIN_WITHDRAWAL_AMOUNT)
            );
        }
    }


    private static void validateSufficientBalance(Account account, double amount) {
        if (account.getBalance() < amount) {
            throw new IllegalStateException(
                    String.format("Insufficient balance. Available: $%.2f, Required: $%.2f",
                            account.getBalance(), amount)
            );
        }
    }

    private void validateDailyWithdrawalLimit(String accountNumber, double amount) {
        double dailyTotal = getTodayWithdrawalTotal(accountNumber);

        if (dailyTotal + amount > DAILY_WITHDRAWAL_LIMIT) {
            throw new IllegalStateException(
                    String.format("Daily withdrawal limit exceeded. Limit: $%.2f, Used: $%.2f",
                            DAILY_WITHDRAWAL_LIMIT, dailyTotal)
            );
        }
    }


    private static Account createTempAccount(String accountNumber) {
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setBalance(5000.0);  // رصيد افتراضي للتجربة
        account.setStatus(AccountStatus.ACTIVE);
        return account;
    }


    private static String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }


    private boolean isValidAccountNumber(String accountNumber) {
        return accountNumber != null &&
                accountNumber.matches("^ACC\\d{8}$"); // ACC متبوع بـ 8 أرقام
    }

    private double getTodayWithdrawalTotal(String accountNumber) {
        return 1200.0;
    }

    private static void validateBasicRequirements(TransferRequest request) {
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
    public static void processPostTransferTasksAsync(Transaction transaction) {
        log.info("🔄 Starting async processing for transfer transaction");
        executeAsyncTasks("Transfer", transaction);
    }

    @Async
    private void processPostDepositTasksAsync(Transaction transaction) {
        log.info("🔄 Starting async processing for deposit transaction");
        executeAsyncTasks("Deposit", transaction);
    }

    @Async
    private void processPostWithdrawalTasksAsync(Transaction transaction) {
        log.info("🔄 Starting async processing for withdrawal transaction");
        executeAsyncTasks("Withdrawal", transaction);
    }


    private static void executeAsyncTasks(String transactionType, Transaction transaction) {
        try {
            log.debug("   📊 {}: Generating audit log", transactionType);
            Thread.sleep(500);

            log.debug("   📧 {}: Sending notifications", transactionType);
            Thread.sleep(500);

            log.debug("   📈 {}: Updating reports", transactionType);
            Thread.sleep(500);

            log.debug("   🔄 {}: Processing complete", transactionType);

            log.info("✅ Async processing completed for {} transaction", transactionType);

        } catch (InterruptedException e) {
            log.error("Async processing interrupted for {} transaction", transactionType, e);
            Thread.currentThread().interrupt();
        }
    }
}