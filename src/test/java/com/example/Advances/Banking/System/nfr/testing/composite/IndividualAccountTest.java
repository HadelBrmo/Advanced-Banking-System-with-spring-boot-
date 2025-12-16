package com.example.Advances.Banking.System.nfr.testing.composite;

import com.example.Advances.Banking.System.patterns.structural.composite.IndividualAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IndividualAccount - Deposit Tests (Updated for zero deposit)")
class IndividualAccountDepositTest {

    private IndividualAccount account;

    @BeforeEach
    void setUp() {
        account = new IndividualAccount("حساب اختبار", "TEST-001", 1000.0);
    }

    @Test
    @DisplayName("Deposit Positive Amount - Should Succeed")
    void depositPositiveAmount_ShouldIncreaseBalance() {
        double initialBalance = account.getBalance();
        double depositAmount = 500.0;

        account.deposit(depositAmount);

        assertEquals(initialBalance + depositAmount, account.getBalance(), 0.001,
                "Balance should increase by deposit amount");
    }

    @Test
    @DisplayName("Deposit Zero Amount - Should Not Throw Exception")
    void depositZeroAmount_ShouldNotThrowException() {
        double initialBalance = account.getBalance();

        assertDoesNotThrow(() -> account.deposit(0.0),
                "Depositing zero should NOT throw exception");

        assertEquals(initialBalance, account.getBalance(), 0.001,
                "Balance should not change after zero deposit");
    }

    @Test
    @DisplayName("Deposit Negative Amount - Should Throw Exception")
    void depositNegativeAmount_ShouldThrowIllegalArgumentException() {
        double initialBalance = account.getBalance();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> account.deposit(-100.0),
                "Depositing negative amount should throw exception"
        );

        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("negative") ||
                        exception.getMessage().contains("cannot"),
                "Exception should mention negative amount");

        assertEquals(initialBalance, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Deposit Small Positive Amount - Should Succeed")
    void depositSmallAmount_ShouldSucceed() {
        double initialBalance = account.getBalance();
        double smallAmount = 0.01;

        account.deposit(smallAmount);

        assertEquals(initialBalance + smallAmount, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Multiple Deposits Including Zero - Should Work")
    void multipleDepositsIncludingZero_ShouldWork() {
        account.deposit(100.0);
        assertEquals(1100.0, account.getBalance(), 0.001);

        account.deposit(0.0);
        assertEquals(1100.0, account.getBalance(), 0.001,
                "Zero deposit should not change balance");

        account.deposit(200.0);
        assertEquals(1300.0, account.getBalance(), 0.001);
    }
}