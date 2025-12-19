package com.example.Advances.Banking.System.patterns.behavioral.state;

public class BankAccount {
    private double balance = 0.0;
    private AccountState state = new ActiveState();

    public void setState(AccountState state) { this.state = state; }
    public double getBalance() { return balance; }
    public void setBalance(double amount) { this.balance = amount; }

    public void deposit(double amount) { state.deposit(this, amount); }
    public void withdraw(double amount) { state.withdraw(this, amount); }

    public AccountState getState() {
        return state;
    }
}