package com.example.Advances.Banking.System.nfr.testing.state;

import com.example.Advances.Banking.System.patterns.behavioral.state.AccountState;
import com.example.Advances.Banking.System.patterns.behavioral.state.BankAccount;
import com.example.Advances.Banking.System.patterns.behavioral.state.SuspendedState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SuspendedStateTest {

    @Test
    void depositShouldNotChangeBalance() {
        BankAccount account = new BankAccount();
        account.setBalance(100);
        AccountState state = new SuspendedState();
        state.deposit(account, 50);
        assertEquals(100, account.getBalance());
    }

    @Test
    void withdrawShouldNotChangeBalance() {
        BankAccount account = new BankAccount();
        account.setBalance(100);
        AccountState state = new SuspendedState();
        state.withdraw(account, 50);
        assertEquals(100, account.getBalance());
    }
}