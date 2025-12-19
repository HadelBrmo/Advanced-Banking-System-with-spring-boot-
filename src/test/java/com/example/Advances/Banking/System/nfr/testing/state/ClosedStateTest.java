package com.example.Advances.Banking.System.nfr.testing.state;

import com.example.Advances.Banking.System.patterns.behavioral.state.ClosedState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.Advances.Banking.System.patterns.behavioral.state.AccountState;
import com.example.Advances.Banking.System.patterns.behavioral.state.BankAccount;

public class ClosedStateTest {

    @Test
    void depositShouldNotChangeBalance() {
        BankAccount account = new BankAccount();
        account.setBalance(100);
        AccountState state = new ClosedState();
        state.deposit(account, 50);
        assertEquals(100, account.getBalance());
    }

    @Test
    void withdrawShouldNotChangeBalance() {
        BankAccount account = new BankAccount();
        account.setBalance(100);
        AccountState state = new ClosedState();
        state.withdraw(account, 50);
        assertEquals(100, account.getBalance());
    }
}