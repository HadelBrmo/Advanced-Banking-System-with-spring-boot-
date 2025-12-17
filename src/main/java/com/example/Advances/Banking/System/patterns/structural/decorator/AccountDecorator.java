package com.example.Advances.Banking.System.patterns.structural.decorator;


public abstract class AccountDecorator implements BankAccount {

    public BankAccount decoratedAccount;

    public AccountDecorator(BankAccount account) {
        this.decoratedAccount = account;
    }

    @Override
    public String getAccountNumber() {
        return decoratedAccount.getAccountNumber();
    }

    @Override
    public double getBalance() {

        return decoratedAccount.getBalance();
    }

    @Override
    public void deposit(double amount) {
        decoratedAccount.deposit(amount);
    }

    @Override
    public boolean withdraw(double amount) {
        return decoratedAccount.withdraw(amount);
    }

    @Override
    public abstract String getDescription();

    @Override
    public abstract double getMonthlyFee();
}