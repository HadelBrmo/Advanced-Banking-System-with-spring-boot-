package com.example.Advances.Banking.System.nfr.testing.decorator;

import com.example.Advances.Banking.System.patterns.structural.decorator.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Decorator Integration Tests")
class DecoratorIntegrationTest {

    private BankAccount fullyDecoratedAccount;

    @BeforeEach
    void setUp() {
        BasicAccount basic = new BasicAccount("ACC123", 1000.0);
        OverdraftProtectionDecorator withOverdraft = new OverdraftProtectionDecorator(basic, 500.0);
        InsuranceDecorator withInsurance = new InsuranceDecorator(withOverdraft, 10000.0);
        fullyDecoratedAccount = new PremiumServicesDecorator(withInsurance);
    }

    @Test
    @DisplayName("Full decorator chain description")
    void getDescription_FullChain() {
        String description = fullyDecoratedAccount.getDescription();
        assertTrue(description.contains("حساب أساسي"));
        assertTrue(description.contains("حماية السحب المكشوف"));
        assertTrue(description.contains("تأمين على الحساب"));
        assertTrue(description.contains("خدمات مميزة"));
    }

    @Test
    @DisplayName("Full decorator chain monthly fee")
    void getMonthlyFee_FullChain() {
        double totalFee = fullyDecoratedAccount.getMonthlyFee();
        assertEquals(15.0, totalFee, 0.001); // 0 (Basic) + 2 (Overdraft) + 3 (Insurance) + 10 (Premium)
    }

    @Test
    @DisplayName("Withdraw using overdraft in decorated chain")
    void withdraw_UsingOverdraft_InDecoratedChain() {
        boolean result = fullyDecoratedAccount.withdraw(1400.0);
        assertTrue(result, "يجب أن ينجح السحب");
        assertEquals(-405.0, fullyDecoratedAccount.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Deposit and withdraw sequence in decorated chain")
    void depositWithdrawSequence_InDecoratedChain() {
        // إيداع 200
        fullyDecoratedAccount.deposit(200.0);
        assertEquals(1200.0, fullyDecoratedAccount.getBalance(), 0.001);

        // سحب 800
        fullyDecoratedAccount.withdraw(800.0);
        assertEquals(400.0, fullyDecoratedAccount.getBalance(), 0.001);

        // سحب 900 بدلاً من 1000 (400 رصيد + 500 سحب مكشوف - الحد الأقصى)
        boolean result = fullyDecoratedAccount.withdraw(900.0);
        assertTrue(result, "يجب أن ينجح السحب باستخدام السحب المكشوف");

        // 400 - 900 = -500 سحب مكشوف + 5 رسوم = 505 سحب مكشوف مستخدم
        // الرصيد = 400 - 900 - 5 = -505
        assertEquals(-505.0, fullyDecoratedAccount.getBalance(), 0.001);
    }

    @Test
    @DisplayName("File insurance claim on decorated account")
    void fileInsuranceClaim_OnDecoratedAccount() {
        PremiumServicesDecorator premium = (PremiumServicesDecorator) fullyDecoratedAccount;
        InsuranceDecorator insured = (InsuranceDecorator) premium.decoratedAccount;

        insured.fileClaim(5000.0);

        assertEquals(6000.0, fullyDecoratedAccount.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Use premium services on decorated account")
    void usePremiumServices_OnDecoratedAccount() {
        PremiumServicesDecorator premiumAccount = (PremiumServicesDecorator) fullyDecoratedAccount;

        premiumAccount.getFinancialAdvice();
        premiumAccount.requestPrioritySupport();
        premiumAccount.getInvestmentOpportunities();

        premiumAccount.deposit(1000.0);
        assertEquals(2000.0, premiumAccount.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Complex transaction flow with all decorators")
    void complexTransactionFlow_WithAllDecorators() {

        fullyDecoratedAccount.deposit(500.0);
        assertEquals(1500.0, fullyDecoratedAccount.getBalance(), 0.001);


        boolean result1 = fullyDecoratedAccount.withdraw(1800.0);
        assertTrue(result1, "يجب أن ينجح السحب");

        assertEquals(-305.0, fullyDecoratedAccount.getBalance(), 0.001);

        fullyDecoratedAccount.deposit(1000.0);
        assertEquals(695.0, fullyDecoratedAccount.getBalance(), 0.001);

        PremiumServicesDecorator premiumAccount = (PremiumServicesDecorator) fullyDecoratedAccount;
        premiumAccount.getFinancialAdvice();

        InsuranceDecorator insuredAccount = (InsuranceDecorator) premiumAccount.decoratedAccount;
        insuredAccount.fileClaim(2000.0);

        assertEquals(2695.0, fullyDecoratedAccount.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Get account number through decorator chain")
    void getAccountNumber_ThroughDecoratorChain() {
        assertEquals("ACC123", fullyDecoratedAccount.getAccountNumber());
    }

    @Test
    @DisplayName("Test multiple account creation with different decorators")
    void multipleAccounts_WithDifferentDecorators() {
        BasicAccount basic1 = new BasicAccount("ACC001", 500.0);
        BasicAccount basic2 = new BasicAccount("ACC002", 2000.0);

        BankAccount account1 = new OverdraftProtectionDecorator(basic1, 300.0);
        BankAccount account2 = new InsuranceDecorator(basic2, 5000.0);
        BankAccount account3 = new PremiumServicesDecorator(
                new InsuranceDecorator(
                        new OverdraftProtectionDecorator(
                                new BasicAccount("ACC003", 1500.0), 1000.0
                        ), 20000.0
                )
        );

        assertNotEquals(account1.getDescription(), account2.getDescription());
        assertNotEquals(account2.getDescription(), account3.getDescription());
        assertNotEquals(account1.getMonthlyFee(), account2.getMonthlyFee());
        assertNotEquals(account2.getMonthlyFee(), account3.getMonthlyFee());

        assertTrue(account1.withdraw(600.0));
        assertEquals(-105.0, account1.getBalance(), 0.001);

        assertTrue(account2.withdraw(1000.0));
        assertEquals(1000.0, account2.getBalance(), 0.001);

        assertTrue(account3.withdraw(2000.0));
        assertEquals(-505.0, account3.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Test deposit after overdraft in decorated chain")
    void testDepositAfterOverdraft() {
        fullyDecoratedAccount.withdraw(1400.0);
        assertEquals(-405.0, fullyDecoratedAccount.getBalance(), 0.001);

        fullyDecoratedAccount.deposit(500.0);
        assertEquals(95.0, fullyDecoratedAccount.getBalance(), 0.001);


        fullyDecoratedAccount.deposit(100.0);
        assertEquals(195.0, fullyDecoratedAccount.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Test exceeding overdraft limit in decorated chain")
    void testExceedingOverdraftLimit() {
        boolean result = fullyDecoratedAccount.withdraw(1600.0);
        assertFalse(result, "يجب أن يفشل السحب لأنه يتجاوز الحد المتاح");
        assertEquals(1000.0, fullyDecoratedAccount.getBalance(), 0.001);
    }
}