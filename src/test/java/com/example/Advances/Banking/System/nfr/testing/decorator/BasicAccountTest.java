package com.example.Advances.Banking.System.nfr.testing.decorator;

import com.example.Advances.Banking.System.patterns.structural.decorator.BasicAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BasicAccount Tests")
class BasicAccountTest {

    private BasicAccount account;

    @BeforeEach
    void setUp() {
        account = new BasicAccount("ACC123", 1000.0);
    }

    @Test
    @DisplayName("Constructor should initialize correctly")
    void constructor_ShouldInitializeCorrectly() {
        assertEquals("ACC123", account.getAccountNumber());
        assertEquals(1000.0, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Deposit should increase balance")
    void deposit_ShouldIncreaseBalance() {
        account.deposit(500.0);
        assertEquals(1500.0, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Deposit with zero should not change balance")
    void deposit_ZeroAmount_ShouldNotChangeBalance() {
        account.deposit(0);
        assertEquals(1000.0, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Deposit with negative amount should not change balance")
    void deposit_NegativeAmount_ShouldNotChangeBalance() {
        account.deposit(-100.0);
        assertEquals(1000.0, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Withdraw with sufficient balance should succeed")
    void withdraw_WithSufficientBalance_ShouldSucceed() {
        boolean result = account.withdraw(300.0);
        assertTrue(result);
        assertEquals(700.0, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Withdraw with insufficient balance should fail")
    void withdraw_WithInsufficientBalance_ShouldFail() {
        boolean result = account.withdraw(1500.0);
        assertFalse(result);
        assertEquals(1000.0, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Withdraw with zero amount should fail")
    void withdraw_ZeroAmount_ShouldFail() {
        boolean result = account.withdraw(0);
        assertFalse(result);
        assertEquals(1000.0, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Withdraw with negative amount should fail")
    void withdraw_NegativeAmount_ShouldFail() {
        boolean result = account.withdraw(-100.0);
        assertFalse(result);
        assertEquals(1000.0, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Get description should return correct string")
    void getDescription_ShouldReturnCorrectString() {
        assertEquals("حساب أساسي", account.getDescription());
    }

    @Test
    @DisplayName("Get monthly fee should be zero")
    void getMonthlyFee_ShouldBeZero() {
        assertEquals(0.0, account.getMonthlyFee(), 0.001);
    }

    @Test
    @DisplayName("Full transaction sequence")
    void fullTransactionSequence() {
        account.deposit(200.0);
        assertEquals(1200.0, account.getBalance(), 0.001);

        account.withdraw(400.0);
        assertEquals(800.0, account.getBalance(), 0.001);

        account.deposit(100.0);
        assertEquals(900.0, account.getBalance(), 0.001);

        boolean result = account.withdraw(1000.0);
        assertFalse(result);
        assertEquals(900.0, account.getBalance(), 0.001);
    }
}