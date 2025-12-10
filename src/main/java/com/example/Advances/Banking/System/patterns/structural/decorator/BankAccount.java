package com.example.Advances.Banking.System.patterns.structural.decorator;


public interface BankAccount {

    String getAccountNumber();
    double getBalance();
    void deposit(double amount);
    boolean withdraw(double amount);


    String getDescription();
    double getMonthlyFee();
}
