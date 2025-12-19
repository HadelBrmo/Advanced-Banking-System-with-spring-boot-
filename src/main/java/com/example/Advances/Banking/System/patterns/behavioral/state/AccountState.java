package com.example.Advances.Banking.System.patterns.behavioral.state;



public interface AccountState {
    void deposit(BankAccount account, double amount);
    void withdraw(BankAccount account, double amount);
}