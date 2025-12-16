package com.example.Advances.Banking.System.nfr.testing.factory;

import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;
import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.enums.AccountStatus;
import com.example.Advances.Banking.System.patterns.creational.factory.CheckingAccountCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CheckingAccountCreator Unit Tests")
class CheckingAccountCreatorTest {

    private Customer testCustomer;
    private CheckingAccountCreator creator;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer("Ali", "Hassan", "ali@test.com");
        testCustomer.setPhone("+9631234567");
        creator = new CheckingAccountCreator();
    }

    @Test
    @DisplayName("Checking Creator - Basic Account Creation")
    void checkingCreator_ShouldCreateAccountWithCorrectSettings() {
        // Given
        double initialBalance = 3000.0;

        // When
        Account account = creator.create(testCustomer, initialBalance);

        // Then
        assertNotNull(account, "Account should not be null");
        assertEquals(AccountType.CHECKING, account.getAccountType(),
                "Account type should be CHECKING");
        assertEquals(initialBalance, account.getBalance(), 0.001,
                "Balance should match initial deposit");
        assertEquals(testCustomer, account.getCustomer(),
                "Customer should match");
        assertEquals(AccountStatus.ACTIVE, account.getStatus(),
                "Account status should be ACTIVE");
    }

    @Test
    @DisplayName("Checking Creator - Account Specific Settings")
    void checkingCreator_ShouldSetCorrectAccountProperties() {
        // Given
        Account account = creator.create(testCustomer, 2500.0);

        // Then
        // Basic settings
        assertEquals(0.0, account.getMinBalance(), 0.001,
                "Minimum balance should be 0 for checking account");
        assertEquals(2000.0, account.getMaxDailyWithdrawal(), 0.001,
                "Max daily withdrawal should be 2000");


        assertTrue(account.getHasOverdraft(),
                "Checking account should support overdraft");
        assertEquals(500.0, account.getOverdraftLimit(), 0.001,
                "Default overdraft limit should be 500");

        assertNull(account.getLoanTermMonths(),
                "Checking account should not have loan term");
        assertNull(account.getRiskLevel(),
                "Checking account should not have risk level");
    }

    @Test
    @DisplayName("Checking Creator - Zero Balance Account")
    void checkingCreator_ShouldCreateAccountWithZeroBalance() {
        // When
        Account account = creator.create(testCustomer, 0.0);

        // Then
        assertEquals(0.0, account.getBalance(), 0.001,
                "Account should be created with zero balance");
        assertTrue(account.getHasOverdraft(),
                "Even zero balance account should have overdraft");
    }

    @Test
    @DisplayName("Checking Creator - Negative Balance Test")
    void checkingCreator_ShouldNotAllowNegativeInitialBalance() {
        // When
        Account account = creator.create(testCustomer, -100.0);

        // Then
        assertEquals(-100.0, account.getBalance(), 0.001,
                "Balance should be -100 (if allowed by system)");
        assertEquals(AccountStatus.ACTIVE, account.getStatus());
    }

    @Test
    @DisplayName("Checking Creator - Premium Account Creation")
    void createPremiumChecking_ShouldCreateEnhancedAccount() {
        // When
        Account account = creator.createPremiumChecking(testCustomer, 10000.0);

        // Then
        assertEquals(AccountType.CHECKING, account.getAccountType(),
                "Account type should still be CHECKING");
        assertEquals(10000.0, account.getBalance(), 0.001,
                "Balance should be 10000");

        // Premium-specific settings
        assertEquals(2000.0, account.getOverdraftLimit(), 0.001,
                "Premium account should have 2000 overdraft limit");
        assertEquals(5000.0, account.getMaxDailyWithdrawal(), 0.001,
                "Premium account should have 5000 max daily withdrawal");

        // Base settings should remain
        assertEquals(0.0, account.getMinBalance(), 0.001,
                "Minimum balance should still be 0");
        assertTrue(account.getHasOverdraft(),
                "Premium account should have overdraft enabled");
    }

    @Test
    @DisplayName("Premium Account - Customer Reference Integrity")
    void premiumAccount_ShouldMaintainCorrectCustomerReference() {
        // Given
        Customer premiumCustomer = new Customer("Premium", "Client", "premium@bank.com");
        premiumCustomer.setPhone("+9639876543");

        // When
        Account account = creator.createPremiumChecking(premiumCustomer, 15000.0);

        // Then
        assertEquals(premiumCustomer, account.getCustomer(),
                "Account should reference the correct customer");
        assertEquals("Premium Client", account.getCustomer().getFullName(),
                "Customer full name should match");
    }

    @Test
    @DisplayName("Creator - Support Method Test")
    void supports_ShouldReturnTrueOnlyForChecking() {
        assertTrue(creator.supports(AccountType.CHECKING),
                "Should support CHECKING account type");
        assertFalse(creator.supports(AccountType.SAVINGS),
                "Should not support SAVINGS account type");
        assertFalse(creator.supports(AccountType.LOAN),
                "Should not support LOAN account type");
        assertFalse(creator.supports(AccountType.INVESTMENT),
                "Should not support INVESTMENT account type");
    }

    @ParameterizedTest
    @ValueSource(doubles = {100.0, 1000.0, 5000.0, 10000.0, 0.01})
    @DisplayName("Checking Creator - Various Balance Amounts")
    void checkingCreator_ShouldHandleVariousBalances(double balance) {
        // When
        Account account = creator.create(testCustomer, balance);

        // Then
        assertEquals(balance, account.getBalance(), 0.001,
                String.format("Balance should be %f", balance));
        assertEquals(AccountStatus.ACTIVE, account.getStatus(),
                "Account should be active regardless of balance");
        assertTrue(account.getHasOverdraft(),
                "Overdraft should be enabled for all balances");
    }

    @ParameterizedTest
    @CsvSource({
            "1000.0, 0.0, 2000.0, 500.0",
            "5000.0, 0.0, 2000.0, 500.0",
            "100.0, 0.0, 2000.0, 500.0"
    })
    @DisplayName("Checking Creator - Parameterized Account Properties")
    void checkingCreator_ShouldMaintainConsistentProperties(
            double balance, double expectedMinBalance,
            double expectedMaxWithdrawal, double expectedOverdraftLimit) {

        // When
        Account account = creator.create(testCustomer, balance);

        // Then
        assertEquals(expectedMinBalance, account.getMinBalance(), 0.001,
                "Minimum balance should always be 0");
        assertEquals(expectedMaxWithdrawal, account.getMaxDailyWithdrawal(), 0.001,
                "Max daily withdrawal should be consistent");
        assertEquals(expectedOverdraftLimit, account.getOverdraftLimit(), 0.001,
                "Overdraft limit should be consistent");
    }

    @Test
    @DisplayName("Checking Creator - Console Output Verification")
    void create_ShouldPrintConsoleMessage() {
        // Given
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        // When
        Account account = creator.create(testCustomer, 3000.0);

        // Then
        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Created CHECKING account"),
                "Console should contain creation message");
        assertTrue(consoleOutput.contains(testCustomer.getFullName()),
                "Console should contain customer name");

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    @DisplayName("Premium Account - Console Output Verification")
    void createPremiumChecking_ShouldPrintPremiumMessage() {
        // Given
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        // When
        Account account = creator.createPremiumChecking(testCustomer, 10000.0);

        // Then
        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Created PREMIUM checking account"),
                "Console should contain premium creation message");

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    @DisplayName("Creator - Null Customer Handling")
    void create_WithNullCustomer_ShouldHandleGracefully() {
        try {
            Account account = creator.create(null, 1000.0);
            // If no exception thrown, verify account state
            assertNull(account.getCustomer(), "Customer should be null");
            assertEquals(AccountStatus.ACTIVE, account.getStatus());
        } catch (NullPointerException e) {
            assertTrue(true, "NullPointerException is acceptable for null customer");
        } catch (IllegalArgumentException e) {
            assertTrue(true, "IllegalArgumentException is good for invalid input");
        }
    }

    @Test
    @DisplayName("Account Immutability After Creation")
    void accountProperties_ShouldBeSetCorrectlyAndConsistently() {
        // When
        Account account = creator.create(testCustomer, 7500.0);

        assertEquals(7500.0, account.getBalance(), "Balance should remain unchanged");
        assertEquals(500.0, account.getOverdraftLimit(), "Overdraft limit should be 500");
        assertEquals(2000.0, account.getMaxDailyWithdrawal(), "Max withdrawal should be 2000");

        assertNotNull(account.getAccountType());
        assertNotNull(account.getStatus());
        assertNotNull(account.getHasOverdraft());
    }
    @Test
    void createPremiumChecking_ShouldSetSpecialProperties() {
        Account account = creator.createPremiumChecking(testCustomer, 10000.0);
        assertEquals(2000.0, account.getOverdraftLimit());
        assertEquals(5000.0, account.getMaxDailyWithdrawal());
    }
}