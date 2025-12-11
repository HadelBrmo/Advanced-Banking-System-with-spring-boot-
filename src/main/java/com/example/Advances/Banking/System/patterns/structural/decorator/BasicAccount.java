package com.example.Advances.Banking.System.patterns.structural.decorator;


public class BasicAccount implements BankAccount {

    private final String accountNumber;
    private double balance;

    public BasicAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    @Override
    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("✅ إيداع " + amount + " إلى الحساب " + accountNumber);
        }
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("✅ سحب " + amount + " من الحساب " + accountNumber);
            return true;
        }
        System.out.println("❌ رصيد غير كافي في الحساب " + accountNumber);
        return false;
    }

    @Override
    public String getDescription() {
        return "حساب أساسي";
    }

    @Override
    public double getMonthlyFee() {
        return 0.0;
    }
}
