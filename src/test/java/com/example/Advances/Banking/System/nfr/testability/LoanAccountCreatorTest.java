package com.example.Advances.Banking.System.nfr.testability;

import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;
import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.enums.AccountStatus;
import com.example.Advances.Banking.System.patterns.creational.factory.LoanAccountCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LoanAccountCreator Unit Tests")
class LoanAccountCreatorTest {

    private Customer testCustomer;
    private LoanAccountCreator creator;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer("Ali", "Hassan", "ali@test.com");
        testCustomer.setPhone("+9631234567");
        creator = new LoanAccountCreator();
    }

    @Test
    @DisplayName("Loan Creator - Basic Loan Creation")
    void loanCreator_ShouldCreateAccountWithCorrectSettings() {
        // Given
        double loanAmount = 10000.0;

        // When
        Account account = creator.create(testCustomer, loanAmount);

        // Then
        assertNotNull(account, "Account should not be null");
        assertEquals(AccountType.LOAN, account.getAccountType(),
                "Account type should be LOAN");
        assertEquals(loanAmount, account.getBalance(), 0.001,
                "Balance should match loan amount");
        assertEquals(testCustomer, account.getCustomer(),
                "Customer should match");
        assertEquals(AccountStatus.ACTIVE, account.getStatus(),
                "Account status should be ACTIVE");

        assertEquals(-loanAmount, account.getMinBalance(), 0.001,
                "Minimum balance should be negative loan amount");
        assertEquals(12, account.getLoanTermMonths(),
                "Default loan term should be 12 months");
        assertFalse(account.getHasOverdraft(),
                "Loan account should not support overdraft");
        assertNull(account.getMaxDailyWithdrawal(),
                "Loan account should not have max daily withdrawal");
        assertNull(account.getOverdraftLimit(),
                "Loan account should not have overdraft limit");
        assertNull(account.getRiskLevel(),
                "Loan account should not have risk level");
    }

    @Test
    @DisplayName("Loan Creator - Custom Term Loan")
    void createLoanAccount_WithCustomTerm_ShouldSetCorrectTerm() {
        // When
        Account account = creator.createLoanAccount(testCustomer, 20000.0, 24);

        // Then
        assertEquals(24, account.getLoanTermMonths(),
                "Loan term should be 24 months");
        assertEquals(-20000.0, account.getMinBalance(), 0.001,
                "Minimum balance should be -20000");
    }

    @Test
    @DisplayName("Loan Creator - Short Term Loan")
    void createShortTermLoan_ShouldCreate6MonthLoan() {
        // When
        Account account = creator.createShortTermLoan(testCustomer, 5000.0);

        // Then
        assertEquals(6, account.getLoanTermMonths(),
                "Short term loan should be 6 months");
        assertEquals(-5000.0, account.getMinBalance(), 0.001);
    }

    @Test
    @DisplayName("Loan Creator - Mortgage Loan")
    void createMortgageLoan_ShouldCreate240MonthLoan() {
        // When
        Account account = creator.createMortgageLoan(testCustomer, 100000.0);

        // Then
        assertEquals(240, account.getLoanTermMonths(),
                "Mortgage loan should be 240 months (20 years)");
        assertEquals(-100000.0, account.getMinBalance(), 0.001);
        System.out.println("Created mortgage: $" + account.getBalance() +
                " for " + account.getLoanTermMonths() + " months");
    }

    @Test
    @DisplayName("Loan Creator - Supports Method")
    void supports_ShouldReturnTrueOnlyForLoan() {
        assertTrue(creator.supports(AccountType.LOAN),
                "Should support LOAN account type");
        assertFalse(creator.supports(AccountType.SAVINGS),
                "Should not support SAVINGS account type");
        assertFalse(creator.supports(AccountType.CHECKING),
                "Should not support CHECKING account type");
        assertFalse(creator.supports(AccountType.INVESTMENT),
                "Should not support INVESTMENT account type");
    }

    @Test
    @DisplayName("Loan Creator - Zero Amount Loan")
    void createLoan_WithZeroAmount_ShouldWork() {
        // When
        Account account = creator.create(testCustomer, 0.0);

        // Then
        assertEquals(0.0, account.getBalance(), 0.001,
                "Zero amount loan should be created");
        assertEquals(0.0, account.getMinBalance(), 0.001,
                "Minimum balance should be 0 for zero loan");
    }

    @Test
    @DisplayName("Loan Creator - Negative Amount (Invalid)")
    void createLoan_WithNegativeAmount_ShouldAllow() {


        // When
        Account account = creator.create(testCustomer, -5000.0);

        // Then
        assertEquals(-5000.0, account.getBalance(), 0.001);
        assertEquals(5000.0, account.getMinBalance(), 0.001,
                "Min balance should be positive for negative loan");
    }

    @ParameterizedTest
    @CsvSource({
            "5000.0, 6,   -5000.0",
            "15000.0, 12, -15000.0",
            "30000.0, 24, -30000.0",
            "100000.0, 240, -100000.0"
    })
    @DisplayName("Loan Creator - Various Loan Amounts and Terms")
    void createLoanAccount_WithVariousAmountsAndTerms(
            double amount, int term, double expectedMinBalance) {

        // When
        Account account = creator.createLoanAccount(testCustomer, amount, term);

        // Then
        assertEquals(amount, account.getBalance(), 0.001);
        assertEquals(term, account.getLoanTermMonths());
        assertEquals(expectedMinBalance, account.getMinBalance(), 0.001);
        assertEquals(AccountType.LOAN, account.getAccountType());
    }

    @Test
    @DisplayName("Loan Creator - Console Output")
    void createLoanAccount_ShouldPrintConsoleMessage() {
        // Given
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        // When
        Account account = creator.create(testCustomer, 15000.0);

        // Then
        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Created LOAN account"),
                "Console should contain loan creation message");
        assertTrue(consoleOutput.contains("Amount: $"),
                "Console should show loan amount");
        assertTrue(consoleOutput.contains("Term: "),
                "Console should show loan term");

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    @DisplayName("Mortgage Loan - Console Output")
    void createMortgageLoan_ShouldPrintMortgageMessage() {
        // Given
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        // When
        Account account = creator.createMortgageLoan(testCustomer, 200000.0);

        // Then
        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Created MORTGAGE loan"),
                "Console should contain mortgage creation message");

        // Reset System.out
        System.setOut(System.out);
    }
}