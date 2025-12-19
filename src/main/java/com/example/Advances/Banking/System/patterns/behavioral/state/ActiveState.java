package com.example.Advances.Banking.System.patterns.behavioral.state;
public class ActiveState implements AccountState {
    @Override
    public void deposit(BankAccount account, double amount) {
        account.setBalance(account.getBalance() + amount);
        System.out.println("Deposited: " + amount);
    }

    @Override
    public void withdraw(BankAccount account, double amount) {
        if (amount > account.getBalance()) {
            System.out.println("Insufficient funds");
        } else {
            account.setBalance(account.getBalance() - amount);
            System.out.println("Withdrawn: " + amount);
        }
    }
}