package com.example.Advances.Banking.System.nfr.testing.decorator;

import com.example.Advances.Banking.System.patterns.structural.decorator.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PremiumServicesDecorator Tests")
class PremiumServicesDecoratorTest {

    private BankAccount basicAccount;
    private PremiumServicesDecorator premiumAccount;

    @BeforeEach
    void setUp() {
        basicAccount = new BasicAccount("ACC123", 1000.0);
        premiumAccount = new PremiumServicesDecorator(basicAccount);
    }

    @Test
    @DisplayName("Constructor should initialize correctly")
    void constructor_ShouldInitializeCorrectly() {
        assertEquals(1000.0, premiumAccount.getBalance(), 0.001);
        assertEquals("ACC123", premiumAccount.getAccountNumber());
    }

    @Test
    @DisplayName("Get description should include premium services")
    void getDescription_ShouldIncludePremiumServices() {
        String description = premiumAccount.getDescription();
        assertTrue(description.contains("خدمات مميزة"));
    }

    @Test
    @DisplayName("Get monthly fee should include premium fee")
    void getMonthlyFee_ShouldIncludePremiumFee() {
        assertEquals(10.0, premiumAccount.getMonthlyFee(), 0.001);
    }

    @Test
    @DisplayName("Get financial advice should print message")
    void getFinancialAdvice_ShouldPrintMessage() {
        premiumAccount.getFinancialAdvice();
    }

    @Test
    @DisplayName("Request priority support should print message")
    void requestPrioritySupport_ShouldPrintMessage() {
        premiumAccount.requestPrioritySupport();
    }

    @Test
    @DisplayName("Get investment opportunities should print message")
    void getInvestmentOpportunities_ShouldPrintMessage() {
        premiumAccount.getInvestmentOpportunities();
    }

    @Test
    @DisplayName("Basic operations should work normally")
    void basicOperations_ShouldWorkNormally() {
        premiumAccount.deposit(500.0);
        assertEquals(1500.0, premiumAccount.getBalance(), 0.001);

        boolean result = premiumAccount.withdraw(300.0);
        assertTrue(result);
        assertEquals(1200.0, premiumAccount.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Use all premium services")
    void useAllPremiumServices() {
        premiumAccount.getFinancialAdvice();
        premiumAccount.requestPrioritySupport();
        premiumAccount.getInvestmentOpportunities();

        premiumAccount.deposit(1000.0);
        assertEquals(2000.0, premiumAccount.getBalance(), 0.001);
    }
}