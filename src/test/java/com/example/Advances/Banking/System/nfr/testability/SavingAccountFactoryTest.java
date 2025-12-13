// ✅ SavingsAccountCreatorTest.java
package com.example.Advances.Banking.System.nfr.testability;

import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;
import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.enums.AccountStatus;
import com.example.Advances.Banking.System.patterns.creational.factory.SavingsAccountCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SavingsAccountCreatorTest {

    private Customer testCustomer;
    private SavingsAccountCreator creator;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer("Ali", "Hassan", "ali@test.com");
        testCustomer.setPhone("+9631234567");
        creator = new SavingsAccountCreator();
    }

    @Test
    void savingsCreator_ShouldCreateAccountWithCorrectSettings() {
        // When
        Account account = creator.create(testCustomer, 5000.0);

        // Then
        assertNotNull(account, "Account should not be null");
        assertEquals(AccountType.SAVINGS, account.getAccountType());
        assertEquals(5000.0, account.getBalance(), 0.001);
        assertEquals(testCustomer, account.getCustomer());
        assertEquals(AccountStatus.ACTIVE, account.getStatus());

        assertEquals(100.0, account.getMinBalance(), 0.001);
        assertEquals(1000.0, account.getMaxDailyWithdrawal(), 0.001);
        assertFalse(account.getHasOverdraft());
    }

    @Test
    void supports_ShouldReturnTrueOnlyForSavings() {
        // Then
        assertTrue(creator.supports(AccountType.SAVINGS));
        assertFalse(creator.supports(AccountType.CHECKING));
        assertFalse(creator.supports(AccountType.LOAN));
        assertFalse(creator.supports(AccountType.INVESTMENT));
    }
}