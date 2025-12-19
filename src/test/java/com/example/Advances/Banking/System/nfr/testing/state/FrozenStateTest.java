package com.example.Advances.Banking.System.nfr.testing.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.Advances.Banking.System.patterns.behavioral.state.AccountState;
import com.example.Advances.Banking.System.patterns.behavioral.state.BankAccount;
import com.example.Advances.Banking.System.patterns.behavioral.state.FrozenState;
import org.junit.jupiter.api.Test;
public class FrozenStateTest {

    @Test
    void depositShouldNotChangeBalance() {
        BankAccount account = new BankAccount();
        account.setBalance(100);
        AccountState state = new FrozenState();
        state.deposit(account, 50);
        assertEquals(100, account.getBalance());
    }

    @Test
    void withdrawShouldNotChangeBalance() {
        BankAccount account = new BankAccount();
        account.setBalance(100);
        AccountState state = new FrozenState();
        state.withdraw(account, 50);
        assertEquals(100, account.getBalance());
    }
}