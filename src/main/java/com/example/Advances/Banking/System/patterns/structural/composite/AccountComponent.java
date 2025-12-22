package com.example.Advances.Banking.System.patterns.structural.composite;


import java.util.List;

public interface AccountComponent {

    String getAccountNumber();
    String getAccountName();
    double getBalance();


    void add(AccountComponent component);
    void remove(AccountComponent component);
    List<AccountComponent> getChildren();


    void deposit(double amount);
    boolean withdraw(double amount);
    void display(int indent);
}
