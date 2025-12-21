package com.example.Advances.Banking.System.core.model;

import com.example.Advances.Banking.System.core.enums.AccountStatus;
import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.exception.InsufficientFundsException;
import com.example.Advances.Banking.System.patterns.behavioral.observer.AccountSubject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "accounts")
@Data
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    @Column(nullable = false)
    private Double balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    private Double minBalance;
    private Double maxDailyWithdrawal;
    private Boolean hasOverdraft;
    private Double overdraftLimit;
    private Integer loanTermMonths;
    private String riskLevel;

    @Transient
    private transient AccountSubject notificationSubject;

    // ⭐⭐ إبقاء Constructors ⭐⭐
    public Account() {
        this.accountNumber = generateAccountNumber();
        this.status = AccountStatus.ACTIVE;
        this.balance = 0.0;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.notificationSubject = new AccountSubject(this.accountNumber);
    }

    public Account(AccountType accountType, Customer customer, double initialBalance) {
        this();
        this.accountType = accountType;
        this.customer = customer;
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount < 0) {
            notificationSubject.triggerEvent(
                    "DEPOSIT_FAILED",
                    amount,
                    "Deposit failed: Amount cannot be negative"
            );
            throw new IllegalArgumentException("Deposit amount cannot be negative");
        }

        if (amount == 0) {
            notificationSubject.triggerEvent(
                    "DEPOSIT",
                    0.0,
                    "Zero deposit - no balance change"
            );
            return;
        }

        if (!status.canTransact()) {
            String errorMsg = String.format("Account %s is %s and cannot transact",
                    accountNumber, status.getDescription());
            notificationSubject.triggerEvent(
                    "TRANSACTION_BLOCKED",
                    amount,
                    errorMsg
            );
            throw new IllegalStateException(errorMsg);
        }

        this.balance += amount;
        this.updatedAt = new Date();


        notificationSubject.triggerEvent(
                "DEPOSIT",
                amount,
                String.format("Deposit of $%.2f to account %s", amount, accountNumber)
        );

        if (balance > 10000) {
            notificationSubject.triggerEvent(
                    "HIGH_BALANCE",
                    balance,
                    "Account balance exceeded $10,000"
            );
        }
    }


    public void withdraw(double amount) {
        if (amount <= 0) {
            notificationSubject.triggerEvent(
                    "WITHDRAWAL_FAILED",
                    amount,
                    "Withdrawal failed: Amount must be positive"
            );
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        if (!status.canTransact()) {
            String errorMsg = String.format("Account %s is %s and cannot transact",
                    accountNumber, status.getDescription());
            notificationSubject.triggerEvent(
                    "TRANSACTION_BLOCKED",
                    amount,
                    errorMsg
            );
            throw new IllegalStateException(errorMsg);
        }

        if (amount > balance) {
            notificationSubject.triggerEvent(
                    "INSUFFICIENT_FUNDS",
                    amount,
                    String.format("Withdrawal failed: Insufficient funds. Balance: $%.2f", balance)
            );
            throw new InsufficientFundsException(accountNumber, balance, amount);
        }

        this.balance -= amount;
        this.updatedAt = new Date();


        notificationSubject.triggerEvent(
                "WITHDRAWAL",
                amount,
                String.format("Withdrawal of $%.2f from account %s", amount, accountNumber)
        );


        if (balance < 100) {
            notificationSubject.triggerEvent(
                    "LOW_BALANCE",
                    balance,
                    "Low balance alert: Account balance below $100"
            );
        }

        if (amount > 5000) {
            notificationSubject.triggerEvent(
                    "LARGE_WITHDRAWAL",
                    amount,
                    "Large withdrawal detected: Over $5,000"
            );
        }
    }


    //Validation
    public boolean canWithdraw(double amount) {

        return status.canTransact() && amount <= balance;
    }


    private String generateAccountNumber() {
        return "ACC-" + System.currentTimeMillis() + "-" +
                (int)(Math.random() * 1000);
    }



    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

}