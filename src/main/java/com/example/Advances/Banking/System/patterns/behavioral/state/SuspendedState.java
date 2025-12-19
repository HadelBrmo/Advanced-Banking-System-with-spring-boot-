package com.example.Advances.Banking.System.patterns.behavioral.state;

public class SuspendedState implements AccountState {
    @Override
    public void deposit(BankAccount account, double amount) {
        System.out.println("Account is suspended. Deposit pending approval.");
    }

    @Override
    public void withdraw(BankAccount account, double amount) {
        System.out.println("Account is suspended. Withdrawal not allowed.");
    }
}