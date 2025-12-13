package com.example.Advances.Banking.System.nfr.testability;

import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;
import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.enums.AccountStatus;
import com.example.Advances.Banking.System.patterns.creational.factory.InvestmentAccountCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InvestmentAccountCreator Unit Tests")
class InvestmentAccountCreatorTest {

    private Customer testCustomer;
    private InvestmentAccountCreator creator;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer("Ali", "Hassan", "ali@test.com");
        testCustomer.setPhone("+9631234567");
        creator = new InvestmentAccountCreator();
    }

    @Test
    @DisplayName("Investment Creator - Basic Investment Creation")
    void investmentCreator_ShouldCreateAccountWithCorrectSettings() {
        // Given
        double initialBalance = 20000.0;

        // When
        Account account = creator.create(testCustomer, initialBalance);

        // Then
        assertNotNull(account, "Account should not be null");
        assertEquals(AccountType.INVESTMENT, account.getAccountType(),
                "Account type should be INVESTMENT");
        assertEquals(initialBalance, account.getBalance(), 0.001,
                "Balance should match initial deposit");
        assertEquals(testCustomer, account.getCustomer(),
                "Customer should match");
        assertEquals(AccountStatus.ACTIVE, account.getStatus(),
                "Account status should be ACTIVE");

        // Investment-specific settings
        assertEquals(1000.0, account.getMinBalance(), 0.001,
                "Minimum balance should be 1000 for investment account");
        assertEquals("MODERATE", account.getRiskLevel(),
                "Default risk level should be MODERATE");
        assertFalse(account.getHasOverdraft(),
                "Investment account should not support overdraft");
        assertNull(account.getMaxDailyWithdrawal(),
                "Investment account should not have max daily withdrawal");
        assertNull(account.getOverdraftLimit(),
                "Investment account should not have overdraft limit");
        assertNull(account.getLoanTermMonths(),
                "Investment account should not have loan term");
    }

    @Test
    @DisplayName("Investment Creator - Custom Risk Level")
    void createInvestmentAccount_WithCustomRisk_ShouldSetCorrectRisk() {
        // When
        Account account = creator.createInvestmentAccount(testCustomer, 30000.0, "HIGH");

        // Then
        assertEquals("HIGH", account.getRiskLevel(),
                "Risk level should be HIGH");
        assertEquals(1000.0, account.getMinBalance(), 0.001,
                "Minimum balance should still be 1000");
    }

    @Test
    @DisplayName("Investment Creator - Conservative Investment")
    void createConservativeInvestment_ShouldSetLowRisk() {
        // When
        Account account = creator.createConservativeInvestment(testCustomer, 15000.0);

        // Then
        assertEquals("LOW", account.getRiskLevel(),
                "Conservative investment should have LOW risk");
        assertEquals(1000.0, account.getMinBalance(), 0.001);
    }

    @Test
    @DisplayName("Investment Creator - Aggressive Investment")
    void createAggressiveInvestment_ShouldSetHighRiskAndHigherMinBalance() {
        // When
        Account account = creator.createAggressiveInvestment(testCustomer, 50000.0);

        // Then
        assertEquals("HIGH", account.getRiskLevel(),
                "Aggressive investment should have HIGH risk");
        assertEquals(5000.0, account.getMinBalance(), 0.001,
                "Aggressive investment should have higher minimum balance (5000)");
    }

    @Test
    @DisplayName("Investment Creator - Supports Method")
    void supports_ShouldReturnTrueOnlyForInvestment() {
        assertTrue(creator.supports(AccountType.INVESTMENT),
                "Should support INVESTMENT account type");
        assertFalse(creator.supports(AccountType.SAVINGS),
                "Should not support SAVINGS account type");
        assertFalse(creator.supports(AccountType.CHECKING),
                "Should not support CHECKING account type");
        assertFalse(creator.supports(AccountType.LOAN),
                "Should not support LOAN account type");
    }

    @Test
    @DisplayName("Investment Creator - Minimum Balance Requirement")
    void createInvestment_WithLowBalance_ShouldStillCreate() {
        // When -
        Account account = creator.create(testCustomer, 500.0);

        // Then -
        assertEquals(500.0, account.getBalance(), 0.001);
        assertEquals(1000.0, account.getMinBalance(), 0.001,
                "Minimum balance should still be 1000 even if actual balance is lower");
    }

    @Test
    @DisplayName("Investment Creator - Various Risk Levels")
    void createInvestmentAccount_WithVariousRiskLevels() {
        // Test different risk levels
        String[] riskLevels = {"LOW", "MODERATE", "HIGH"};

        for (String riskLevel : riskLevels) {
            Account account = creator.createInvestmentAccount(testCustomer, 10000.0, riskLevel);
            assertEquals(riskLevel, account.getRiskLevel(),
                    "Risk level should be " + riskLevel);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "LOW,     10000.0, 1000.0",
            "MODERATE, 25000.0, 1000.0",
            "HIGH,     50000.0, 1000.0"
    })
    @DisplayName("Investment Creator - Parameterized Risk Levels")
    void createInvestmentAccount_WithParameterizedData(
            String riskLevel, double balance, double expectedMinBalance) {

        // When
        Account account = creator.createInvestmentAccount(testCustomer, balance, riskLevel);

        // Then
        assertEquals(riskLevel, account.getRiskLevel());
        assertEquals(balance, account.getBalance(), 0.001);
        assertEquals(expectedMinBalance, account.getMinBalance(), 0.001);
    }

    @Test
    @DisplayName("Aggressive Investment - Higher Minimum Balance")
    void aggressiveInvestment_ShouldHaveSpecialMinBalance() {
        // When
        Account account = creator.createAggressiveInvestment(testCustomer, 75000.0);

        // Then
        assertEquals("HIGH", account.getRiskLevel());
        assertEquals(5000.0, account.getMinBalance(), 0.001,
                "Aggressive investment should have 5000 min balance");
        assertNotEquals(1000.0, account.getMinBalance(), 0.001,
                "Should not have the default 1000 min balance");
    }

    @Test
    @DisplayName("Investment Creator - Console Output")
    void createInvestmentAccount_ShouldPrintConsoleMessage() {
        // Given
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        // When
        Account account = creator.create(testCustomer, 25000.0);

        // Then
        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Created INVESTMENT account"),
                "Console should contain investment creation message");
        assertTrue(consoleOutput.contains("Risk: "),
                "Console should show risk level");
        assertTrue(consoleOutput.contains("Balance: $"),
                "Console should show balance");

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    @DisplayName("Aggressive Investment - Console Output")
    void createAggressiveInvestment_ShouldPrintSpecialMessage() {
        // Given
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        // When
        Account account = creator.createAggressiveInvestment(testCustomer, 100000.0);

        // Then
        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("🚀 Created AGGRESSIVE investment account"),
                "Console should contain aggressive investment message");

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    @DisplayName("Investment Creator - Null Risk Level Handling")
    void createInvestmentAccount_WithNullRisk_ShouldHandleGracefully() {


        try {
            Account account = creator.createInvestmentAccount(testCustomer, 10000.0, null);

            assertNull(account.getRiskLevel(), "Risk level should be null");
        } catch (NullPointerException e) {

            assertTrue(true, "NullPointerException is acceptable for null risk");
        } catch (IllegalArgumentException e) {

            assertTrue(true, "IllegalArgumentException is good for invalid risk");
        }
    }
}
