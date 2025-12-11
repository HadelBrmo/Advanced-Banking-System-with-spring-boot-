package com.example.Advances.Banking.System.subsystem.account.service;


import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;
import com.example.Advances.Banking.System.core.enums.AccountType;
//import com.example.Advances.Banking.System.core.factory.AccountFactory;
import com.example.Advances.Banking.System.patterns.behavioral.observer.*;
import com.example.Advances.Banking.System.patterns.creational.factory.AccountFactory;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final NotificationManager notificationManager;

    public AccountService() {
        this.notificationManager = new NotificationManager();
    }


    public Account createAccountWithNotifications(
            AccountType type,
            Customer customer,
            double initialBalance,
            String email,
            String phone) {

        Account account = AccountFactory.createAccount(type, customer, initialBalance);


       // setupAccountNotifications(account, email, phone, customer);


        account.getNotificationSubject().triggerEvent(
                "ACCOUNT_CREATED",
                initialBalance,
                String.format("New %s account created with initial balance $%.2f",
                        type.getDescription(), initialBalance)
        );

        System.out.println("✅ Account created with notifications: " + account.getAccountNumber());
        return account;
    }

 /*
    private void setupAccountNotifications(Account account, String email,
                                           String phone, Customer customer) {

        String userId = customer.getCustomerId();
        String customerName = customer.getFullName();

        notificationManager.setupDefaultNotifications(
                account.getAccountNumber(),
                email,
                phone,
                userId,
                customerName
        );


        if (account.getAccountType() == AccountType.LOAN) {
            notificationManager.addObserverToAccount(
                    account.getAccountNumber(),
                    new LoanNotifier()
            );
        }
    }

  */
    public void processDeposit(Account account, double amount) {
        try {
            System.out.println("\n Processing deposit...");
            System.out.println("Account: " + account.getAccountNumber());
            System.out.println("Amount: $" + amount);

            account.deposit(amount);

            System.out.println(" Deposit successful!");
            System.out.println("New balance: $" + account.getBalance());

        } catch (Exception e) {
            System.out.println(" Deposit failed: " + e.getMessage());
        }
    }


    public void processWithdrawal(Account account, double amount) {
        try {
            System.out.println("\n💸 Processing withdrawal...");
            System.out.println("Account: " + account.getAccountNumber());
            System.out.println("Amount: $" + amount);
            System.out.println("Current balance: $" + account.getBalance());

            account.withdraw(amount);

            System.out.println(" Withdrawal successful!");
            System.out.println("New balance: $" + account.getBalance());

        } catch (Exception e) {
            System.out.println(" Withdrawal failed: " + e.getMessage());
        }
    }


    public int getNotificationCount(String accountNumber) {
        return notificationManager.getObserverCount(accountNumber);
    }

    public void addNotificationChannel(String accountNumber, AccountObserver observer) {
        notificationManager.addObserverToAccount(accountNumber, observer);
        System.out.println("➕ Added notification channel: " + observer.getObserverId());
    }
}

