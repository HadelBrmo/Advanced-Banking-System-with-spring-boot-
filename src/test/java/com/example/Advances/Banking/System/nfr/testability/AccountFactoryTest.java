package com.example.Advances.Banking.System.nfr.testability;

import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;
import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.patterns.creational.factory.SavingsAccountFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AccountFactoryTest {

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer("Ali", "Hassan", "ali@test.com");
        testCustomer.setPhone("+9631234567");
    }

    @Test
    void savingsFactory_ShouldCreateAccountWithCorrectSettings() {
        // Given
        SavingsAccountFactory factory = new SavingsAccountFactory();

        // When
        Account account = factory.createAccount(testCustomer, 5000.0);

        // Then
        assertNotNull(account, "Account should not be null");
        assertEquals(AccountType.SAVINGS, account.getAccountType(), "Account type should be SAVINGS");
        assertEquals(5000.0, account.getBalance(), 0.001, "Balance should be 5000");
        assertEquals(testCustomer, account.getCustomer(), "Customer should match");

        // Savings account specific settings
        assertEquals(100.0, account.getMinBalance(), 0.001, "Minimum balance should be 100");
        assertEquals(1000.0, account.getMaxDailyWithdrawal(), 0.001, "Max daily withdrawal should be 1000");
        assertFalse(account.getHasOverdraft(), "Savings account should not support overdraft");
    }
}