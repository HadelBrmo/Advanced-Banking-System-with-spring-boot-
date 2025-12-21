package com.example.Advances.Banking.System.patterns.behavioral.state;

import com.example.Advances.Banking.System.patterns.behavioral.state.AccountState;
import com.example.Advances.Banking.System.patterns.behavioral.state.BankAccount;

public class FrozenState implements AccountState {
    @Override
    public void deposit(BankAccount account, double amount) {
        System.out.println("Account is frozen. Cannot deposit.");
    }

    @Override
    public void withdraw(BankAccount account, double amount) {
        System.out.println("Account is frozen. Cannot withdraw.");
    }

    @Override
    public String getStateName() {
        return "FROZEN";
    }
}