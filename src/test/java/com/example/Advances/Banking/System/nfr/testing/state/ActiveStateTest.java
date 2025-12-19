package com.example.Advances.Banking.System.nfr.testing.state;

import com.example.Advances.Banking.System.patterns.behavioral.state.AccountState;
import com.example.Advances.Banking.System.patterns.behavioral.state.ActiveState;
import com.example.Advances.Banking.System.patterns.behavioral.state.BankAccount;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ActiveStateTest {

    @Test
    void depositShouldIncreaseBalance() {
        BankAccount account = new BankAccount();
        AccountState state = new ActiveState();
        state.deposit(account, 100);
        assertEquals(100, account.getBalance());
    }

    @Test
    void withdrawShouldDecreaseBalance() {
        BankAccount account = new BankAccount();
        account.setBalance(200);
        AccountState state = new ActiveState();
        state.withdraw(account, 50);
        assertEquals(150, account.getBalance());
    }

    @Test
    void withdrawShouldFailIfInsufficientFunds() {
        BankAccount account = new BankAccount();
        account.setBalance(30);
        AccountState state = new ActiveState();
        state.withdraw(account, 100);
        assertEquals(30, account.getBalance());
    }
}