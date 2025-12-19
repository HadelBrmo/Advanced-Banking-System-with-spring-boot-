package com.example.Advances.Banking.System.patterns.behavioral.state;

public class ClosedState implements AccountState {
    @Override
    public void deposit(BankAccount account, double amount) {
        System.out.println("Account is closed. No operations allowed.");
    }

    @Override
    public void withdraw(BankAccount account, double amount) {
        System.out.println("Account is closed. No operations allowed.");
    }
}