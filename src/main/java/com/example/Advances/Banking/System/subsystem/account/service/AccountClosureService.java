package com.example.Advances.Banking.System.subsystem.account.service;


import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.enums.AccountStatus;
import com.example.Advances.Banking.System.patterns.behavioral.observer.NotificationManager;
import org.springframework.stereotype.Service;

@Service
public class AccountClosureService {

    private final NotificationManager notificationManager;

    public AccountClosureService() {
        this.notificationManager = new NotificationManager();
    }


    public void closeAccount(Account account, String closureReason) {
        System.out.println("🔒 Closing account: " + account.getAccountNumber());


        if (!canCloseAccount(account)) {
            throw new IllegalStateException("Cannot close account. Balance must be zero.");
        }

        // تغيير الحالة
        account.setStatus(AccountStatus.CLOSED);

        // تسجيل في السجلات
        logAccountClosure(account, closureReason);

        // إرسال إشعار
        sendClosureNotification(account, closureReason);

        System.out.println("✅ Account closed successfully");
    }

    public void freezeAccount(Account account, String freezeReason) {
        System.out.println("❄️ Freezing account: " + account.getAccountNumber());

        account.setStatus(AccountStatus.FROZEN);

        // إرسال إشعار
        notificationManager.sendEvent(
                account.getAccountNumber(),
                "ACCOUNT_FROZEN",
                0.0,
                "Account frozen: " + freezeReason
        );

        System.out.println("✅ Account frozen");
    }


    public void suspendAccount(Account account, String suspensionReason, int days) {
        System.out.println("⏸️ Suspending account: " + account.getAccountNumber());

        account.setStatus(AccountStatus.SUSPENDED);

        // إرسال إشعار
        notificationManager.sendEvent(
                account.getAccountNumber(),
                "ACCOUNT_SUSPENDED",
                0.0,
                "Account suspended for " + days + " days: " + suspensionReason
        );

        System.out.println("✅ Account suspended");
    }


    public void activateAccount(Account account) {
        System.out.println("🌞 Activating account: " + account.getAccountNumber());

        account.setStatus(AccountStatus.ACTIVE);

        // إرسال إشعار
        notificationManager.sendEvent(
                account.getAccountNumber(),
                "ACCOUNT_ACTIVATED",
                0.0,
                "Account reactivated"
        );

        System.out.println("✅ Account activated");
    }


    private boolean canCloseAccount(Account account) {

        return account.getBalance() == 0.0;
    }


    private void logAccountClosure(Account account, String reason) {
        System.out.println("📝 Account Closure Log:");
        System.out.println("   Account Number: " + account.getAccountNumber());
        System.out.println("   Customer: " + account.getCustomer().getFullName());
        System.out.println("   Closing Date: " + java.time.LocalDateTime.now());
        System.out.println("   Reason: " + reason);
        System.out.println("   Final Balance: $" + account.getBalance());
    }


    private void sendClosureNotification(Account account, String reason) {
        notificationManager.sendEvent(
                account.getAccountNumber(),
                "ACCOUNT_CLOSED",
                0.0,
                "Account closed. Reason: " + reason
        );
    }
}
