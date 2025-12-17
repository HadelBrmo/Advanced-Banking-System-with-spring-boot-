package com.example.Advances.Banking.System.nfr.testing.decorator;

import com.example.Advances.Banking.System.patterns.structural.decorator.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OverdraftProtectionDecorator Tests")
class OverdraftProtectionDecoratorTest {

    private BasicAccount basicAccount;
    private OverdraftProtectionDecorator overdraftAccount;

    @BeforeEach
    void setUp() {
        basicAccount = new BasicAccount("ACC123", 1000.0);
        overdraftAccount = new OverdraftProtectionDecorator(basicAccount, 500.0);
    }

    @Test
    @DisplayName("Constructor should initialize correctly")
    void constructor_ShouldInitializeCorrectly() {
        assertEquals("ACC123", overdraftAccount.getAccountNumber());
        assertEquals(1000.0, overdraftAccount.getBalance(), 0.001); // 1000 - 0
        assertEquals(500.0, overdraftAccount.getOverdraftLimit(), 0.001);
        assertEquals(0.0, overdraftAccount.getCurrentOverdraftUsed(), 0.001);
        assertEquals(500.0, overdraftAccount.getAvailableOverdraft(), 0.001); // 500 - 0
        assertEquals(1500.0, overdraftAccount.getTotalAvailableBalance(), 0.001); // 1000 + 500
        assertEquals(1000.0, overdraftAccount.getRealBalance(), 0.001);
    }

    @Test
    @DisplayName("Normal withdrawal within balance")
    void withdraw_WithinBalance_ShouldWorkNormally() {
        boolean result = overdraftAccount.withdraw(800.0);

        assertTrue(result, "يجب أن ينجح السحب");
        assertEquals(200.0, basicAccount.getBalance(), 0.001, "يجب أن يصبح الرصيد 200");
        assertEquals(200.0, overdraftAccount.getBalance(), 0.001); // 200 - 0
        assertEquals(0.0, overdraftAccount.getCurrentOverdraftUsed(), 0.001);
        assertEquals(500.0, overdraftAccount.getAvailableOverdraft(), 0.001); // 500 - 0
        assertEquals(700.0, overdraftAccount.getTotalAvailableBalance(), 0.001); // 200 + 500
    }

    @Test
    @DisplayName("Withdrawal using overdraft")
    void withdraw_UsingOverdraft_ShouldSucceed() {
        boolean result = overdraftAccount.withdraw(1200.0);

        assertTrue(result, "يجب أن ينجح السحب باستخدام السحب المكشوف");
        assertEquals(0.0, basicAccount.getBalance(), 0.001, "يجب أن يصبح الرصيد 0");
        assertEquals(-205.0, overdraftAccount.getBalance(), 0.001); // 0 - 205
        assertEquals(205.0, overdraftAccount.getCurrentOverdraftUsed(), 0.001, "200 + 5 رسوم");
        assertEquals(295.0, overdraftAccount.getAvailableOverdraft(), 0.001, "500 - 205");
        assertEquals(500.0, overdraftAccount.getTotalAvailableBalance(), 0.001); // 0 + 500
    }

    @Test
    @DisplayName("Withdrawal exceeding total available should fail")
    void withdraw_ExceedingTotalAvailable_ShouldFail() {
        boolean result = overdraftAccount.withdraw(1600.0);

        assertFalse(result, "يجب أن يفشل السحب لأنه يتجاوز الحد المتاح");
        assertEquals(1000.0, basicAccount.getBalance(), 0.001, "يجب أن يبقى الرصيد كما هو");
        assertEquals(0.0, overdraftAccount.getCurrentOverdraftUsed(), 0.001);
        assertEquals(500.0, overdraftAccount.getAvailableOverdraft(), 0.001);
    }

    @Test
    @DisplayName("Overdraft fee should be applied")
    void withdraw_ShouldApplyOverdraftFee() {
        boolean result = overdraftAccount.withdraw(1005.0);

        assertTrue(result, "يجب أن ينجح السحب");
        assertEquals(0.0, basicAccount.getBalance(), 0.001);
        assertEquals(10.0, overdraftAccount.getCurrentOverdraftUsed(), 0.001, "5 + 5 رسوم");
        assertEquals(490.0, overdraftAccount.getAvailableOverdraft(), 0.001, "500 - 10");
        assertEquals(-10.0, overdraftAccount.getBalance(), 0.001); // 0 - 10
    }

    @Test
    @DisplayName("Test total available balance")
    void testTotalAvailableBalance() {
        assertEquals(1500.0, overdraftAccount.getTotalAvailableBalance(), 0.001);

        overdraftAccount.withdraw(1200.0);
        assertEquals(500.0, overdraftAccount.getTotalAvailableBalance(), 0.001); // 0 + 500

        overdraftAccount.deposit(100.0);
        assertEquals(500.0, overdraftAccount.getTotalAvailableBalance(), 0.001); // 0 + 500

        overdraftAccount.deposit(200.0);
        assertEquals(595.0, overdraftAccount.getTotalAvailableBalance(), 0.001); // 95 + 500
    }

    @Test
    @DisplayName("Multiple withdrawals with overdraft")
    void multipleWithdrawals_WithOverdraft() {

        boolean result1 = overdraftAccount.withdraw(800.0);
        assertTrue(result1);
        assertEquals(200.0, basicAccount.getBalance(), 0.001);
        assertEquals(0.0, overdraftAccount.getCurrentOverdraftUsed(), 0.001);


        boolean result2 = overdraftAccount.withdraw(300.0);
        assertTrue(result2);
        assertEquals(0.0, basicAccount.getBalance(), 0.001);
        assertEquals(105.0, overdraftAccount.getCurrentOverdraftUsed(), 0.001, "100 + 5 رسوم");


        overdraftAccount.deposit(200.0);
        assertEquals(95.0, basicAccount.getBalance(), 0.001, "200 - 105");
        assertEquals(0.0, overdraftAccount.getCurrentOverdraftUsed(), 0.001);
    }

    @Test
    @DisplayName("Deposit should pay overdraft first")
    void deposit_ShouldPayOverdraftFirst() {
        overdraftAccount.withdraw(1200.0);
        assertEquals(0.0, basicAccount.getBalance(), 0.001);
        assertEquals(205.0, overdraftAccount.getCurrentOverdraftUsed(), 0.001);

        overdraftAccount.deposit(100.0);
        assertEquals(0.0, basicAccount.getBalance(), 0.001, "يجب أن يبقى 0 لأن الإيداع يذهب للسحب المكشوف أولاً");
        assertEquals(105.0, overdraftAccount.getCurrentOverdraftUsed(), 0.001, "205 - 100");

        overdraftAccount.deposit(200.0);
        assertEquals(95.0, basicAccount.getBalance(), 0.001, "200 - 105");
        assertEquals(0.0, overdraftAccount.getCurrentOverdraftUsed(), 0.001);
    }

    @Test
    @DisplayName("Get description should include overdraft info")
    void getDescription_ShouldIncludeOverdraftInfo() {
        String description = overdraftAccount.getDescription();
        assertTrue(description.contains("حساب أساسي"));
        assertTrue(description.contains("حماية السحب المكشوف"));
        assertTrue(description.contains("500.0"));
    }

    @Test
    @DisplayName("Get monthly fee should include overdraft fee")
    void getMonthlyFee_ShouldIncludeOverdraftFee() {
        assertEquals(2.0, overdraftAccount.getMonthlyFee(), 0.001);
    }
}