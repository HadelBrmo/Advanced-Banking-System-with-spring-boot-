package com.example.Advances.Banking.System.core.model;

import com.example.Advances.Banking.System.core.enums.AccountStatus;
import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.exception.InsufficientFundsException;
import com.example.Advances.Banking.System.patterns.behavioral.observer.AccountSubject;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "accounts")
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

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Integer getLoanTermMonths() {
        return loanTermMonths;
    }

    public void setLoanTermMonths(Integer loanTermMonths) {
        this.loanTermMonths = loanTermMonths;
    }

    public Double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(Double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    public Boolean getHasOverdraft() {
        return hasOverdraft;
    }

    public void setHasOverdraft(Boolean hasOverdraft) {
        this.hasOverdraft = hasOverdraft;
    }

    public Double getMaxDailyWithdrawal() {
        return maxDailyWithdrawal;
    }

    public void setMaxDailyWithdrawal(Double maxDailyWithdrawal) {
        this.maxDailyWithdrawal = maxDailyWithdrawal;
    }

    public Double getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(Double minBalance) {
        this.minBalance = minBalance;
    }

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
        if (amount <= 0) {
            notificationSubject.triggerEvent(
                    "DEPOSIT_FAILED",
                    amount,
                    "Deposit failed: Amount must be positive"
            );
            throw new IllegalArgumentException("Deposit amount must be positive");
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


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public AccountType getAccountType() { return accountType; }
    public void setAccountType(AccountType accountType) { this.accountType = accountType; }

    public AccountStatus getStatus() { return status; }
    public void setStatus(AccountStatus status) { this.status = status; }

    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public AccountSubject getNotificationSubject() {
        if (notificationSubject == null) {
            notificationSubject = new AccountSubject(accountNumber);
        }
        return notificationSubject;
    }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}

