package com.example.Advances.Banking.System.nfr.testing.factory;

import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;
import com.example.Advances.Banking.System.patterns.creational.factory.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AccountFactory Integration Test (Without Spring)")
class AccountFactoryIntegrationWithoutSpringTest {

    private AccountFactory accountFactory;
    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer("Ali", "Hassan", "ali@test.com");
        testCustomer.setPhone("+9631234567");

        List<AccountCreator> creators = List.of(
                new SavingsAccountCreator(),
                new CheckingAccountCreator(),
                new LoanAccountCreator(),
                new InvestmentAccountCreator()
        );

        accountFactory = new AccountFactoryImpl(creators);
    }

    @Test
    @DisplayName("Integration - Create All Account Types")
    void createAllAccountTypes_Integration() {
        System.out.println("\n=== Integration Test: Creating All Account Types ===\n");

        AccountType[] types = AccountType.values();
        int successCount = 0;

        for (AccountType type : types) {
            try {
                System.out.print("Creating " + type + " account... ");
                Account account = accountFactory.createAccount(type, testCustomer, 10000.0);

                assertNotNull(account, "Account should not be null");
                assertEquals(type, account.getAccountType(),
                        "Account type should match requested type");
                assertEquals(10000.0, account.getBalance(), 0.001,
                        "Balance should be 10000");

                System.out.println("✓ SUCCESS");
                successCount++;


                verifyAccountProperties(account, type);

            } catch (Exception e) {
                System.out.println("✗ FAILED: " + e.getMessage());
                e.printStackTrace();
            }
        }

        System.out.println("\n=== Results: " + successCount + "/" + types.length + " account types created successfully ===");
        assertTrue(successCount > 0, "Should create at least one account type");
    }

    @Test
    @DisplayName("Integration - Savings Account Specific Properties")
    void integration_SavingsAccount_ShouldHaveCorrectProperties() {
        // When
        Account account = accountFactory.createAccount(AccountType.SAVINGS, testCustomer, 5000.0);

        // Then - General properties
        assertEquals(AccountType.SAVINGS, account.getAccountType());
        assertEquals(5000.0, account.getBalance(), 0.001);

        // Then - Savings-specific properties
        assertEquals(100.0, account.getMinBalance(), 0.001);
        assertEquals(1000.0, account.getMaxDailyWithdrawal(), 0.001);
        assertFalse(account.getHasOverdraft());
        assertNull(account.getOverdraftLimit());
        assertNull(account.getLoanTermMonths());
        assertNull(account.getRiskLevel());
    }

    @Test
    @DisplayName("Integration - Checking Account Specific Properties")
    void integration_CheckingAccount_ShouldHaveCorrectProperties() {
        // When
        Account account = accountFactory.createAccount(AccountType.CHECKING, testCustomer, 3000.0);

        // Then - General properties
        assertEquals(AccountType.CHECKING, account.getAccountType());

        // Then - Checking-specific properties
        assertEquals(0.0, account.getMinBalance(), 0.001);
        assertEquals(2000.0, account.getMaxDailyWithdrawal(), 0.001);
        assertTrue(account.getHasOverdraft());
        assertEquals(500.0, account.getOverdraftLimit(), 0.001);
        assertNull(account.getLoanTermMonths());
        assertNull(account.getRiskLevel());
    }

    @Test
    @DisplayName("Integration - Loan Account Specific Properties")
    void integration_LoanAccount_ShouldHaveCorrectProperties() {
        // When
        Account account = accountFactory.createAccount(AccountType.LOAN, testCustomer, 15000.0);

        // Then - General properties
        assertEquals(AccountType.LOAN, account.getAccountType());

        // Then - Loan-specific properties
        assertEquals(-15000.0, account.getMinBalance(), 0.001);
        assertEquals(12, account.getLoanTermMonths()); // Default term
        assertFalse(account.getHasOverdraft());
        assertNull(account.getMaxDailyWithdrawal());
        assertNull(account.getOverdraftLimit());
        assertNull(account.getRiskLevel());
    }

    @Test
    @DisplayName("Integration - Investment Account Specific Properties")
    void integration_InvestmentAccount_ShouldHaveCorrectProperties() {
        // When
        Account account = accountFactory.createAccount(AccountType.INVESTMENT, testCustomer, 25000.0);

        // Then - General properties
        assertEquals(AccountType.INVESTMENT, account.getAccountType());

        // Then - Investment-specific properties
        assertEquals(1000.0, account.getMinBalance(), 0.001);
        assertEquals("MODERATE", account.getRiskLevel()); // Default risk
        assertFalse(account.getHasOverdraft());
        assertNull(account.getMaxDailyWithdrawal());
        assertNull(account.getOverdraftLimit());
        assertNull(account.getLoanTermMonths());
    }

    @Test
    @DisplayName("Integration - Factory with Multiple Customers")
    void integration_FactoryWithMultipleCustomers() {
        Customer[] customers = {
                new Customer("Customer1", "Test", "customer1@test.com"),
                new Customer("Customer2", "Test", "customer2@test.com"),
                new Customer("Customer3", "Test", "customer3@test.com")
        };

        for (Customer customer : customers) {
            Account account = accountFactory.createAccount(AccountType.SAVINGS, customer, 2000.0);
            assertEquals(customer, account.getCustomer(),
                    "Account should reference correct customer");
            assertEquals(customer.getFullName(), account.getCustomer().getFullName());
        }
    }

    @Test
    @DisplayName("Integration - Various Balance Amounts")
    void integration_VariousBalanceAmounts() {
        double[] balances = {0.0, 100.0, 1000.0, 5000.0, 10000.0, 50000.0};

        for (double balance : balances) {
            Account account = accountFactory.createAccount(AccountType.CHECKING, testCustomer, balance);
            assertEquals(balance, account.getBalance(), 0.001,
                    "Account balance should be " + balance);
        }
    }

    private void verifyAccountProperties(Account account, AccountType type) {

        assertNotNull(account.getAccountType());
        assertNotNull(account.getStatus());
        assertNotNull(account.getCustomer());

        switch (type) {
            case SAVINGS:
                assertNotNull(account.getMinBalance());
                assertNotNull(account.getMaxDailyWithdrawal());
                assertFalse(account.getHasOverdraft());
                break;
            case CHECKING:
                assertNotNull(account.getMinBalance());
                assertNotNull(account.getMaxDailyWithdrawal());
                assertTrue(account.getHasOverdraft());
                assertNotNull(account.getOverdraftLimit());
                break;
            case LOAN:
                assertNotNull(account.getMinBalance());
                assertNotNull(account.getLoanTermMonths());
                assertFalse(account.getHasOverdraft());
                break;
            case INVESTMENT:
                assertNotNull(account.getMinBalance());
                assertNotNull(account.getRiskLevel());
                assertFalse(account.getHasOverdraft());
                break;
        }
    }
}