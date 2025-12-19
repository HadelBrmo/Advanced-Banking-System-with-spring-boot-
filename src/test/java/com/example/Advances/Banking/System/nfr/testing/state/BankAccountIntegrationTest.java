package com.example.Advances.Banking.System.nfr.testing.state;


import com.example.Advances.Banking.System.patterns.behavioral.state.BankAccount;
import com.example.Advances.Banking.System.patterns.behavioral.state.ClosedState;
import com.example.Advances.Banking.System.patterns.behavioral.state.FrozenState;
import com.example.Advances.Banking.System.patterns.behavioral.state.SuspendedState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BankAccountIntegrationTest {

    @Test
    void testStateTransitionsAndBehavior() {
        BankAccount account = new BankAccount();

        // Active
        account.deposit(200);
        account.withdraw(50);
        assertEquals(150, account.getBalance());

        // Frozen
        account.setState(new FrozenState());
        account.deposit(100);
        account.withdraw(50);
        assertEquals(150, account.getBalance());

        // Suspended
        account.setState(new SuspendedState());
        account.deposit(100);
        account.withdraw(50);
        assertEquals(150, account.getBalance());

        // Closed
        account.setState(new ClosedState());
        account.deposit(100);
        account.withdraw(50);
        assertEquals(150, account.getBalance());
    }
}