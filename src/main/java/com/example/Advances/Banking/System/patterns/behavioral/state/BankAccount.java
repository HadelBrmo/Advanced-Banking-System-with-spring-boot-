package com.example.Advances.Banking.System.patterns.behavioral.state;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BankAccount {
    private double balance = 0.0;
    private AccountState state = new ActiveState();

    public void deposit(double amount) { state.deposit(this, amount); }
    public void withdraw(double amount) { state.withdraw(this, amount); }

}