package com.example.Advances.Banking.System.nfr.testing.strategy;

import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.patterns.behavioral.strategy.InterestStrategy;
import com.example.Advances.Banking.System.patterns.behavioral.strategy.InterestStrategyResolver;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InterestStrategyIntegrationTest {

    @Test
    void testCompleteStrategyResolutionAndCalculation() {
        InterestStrategyResolver resolver = new InterestStrategyResolver();

        // Test Savings Account
        InterestStrategy savingsStrategy = resolver.resolve(AccountType.SAVINGS);
        assertNotNull(savingsStrategy, "Savings strategy should not be null");
        double savingsInterest = savingsStrategy.calculateInterest(1000.0);
        assertEquals(20.0, savingsInterest, 0.001, "Savings interest should be 2% of 1000");

        // Test Loan Account
        InterestStrategy loanStrategy = resolver.resolve(AccountType.LOAN);
        assertNotNull(loanStrategy, "Loan strategy should not be null");
        double loanInterest = loanStrategy.calculateInterest(-10000.0);
        assertEquals(500.0, loanInterest, 0.001, "Loan interest should be 5% of 10000");

        // Test Checking Account
        InterestStrategy checkingStrategy = resolver.resolve(AccountType.CHECKING);
        assertNotNull(checkingStrategy, "Checking strategy should not be null");
        double checkingInterest = checkingStrategy.calculateInterest(5000.0);
        assertEquals(0.0, checkingInterest, 0.001, "Checking account should have zero interest");

        // Test Investment Account (assuming it uses compound interest)
        InterestStrategy investmentStrategy = resolver.resolve(AccountType.INVESTMENT);
        assertNotNull(investmentStrategy, "Investment strategy should not be null");
        double investmentInterest = investmentStrategy.calculateInterest(2000.0);
        assertTrue(investmentInterest > 0, "Investment interest should be positive");

        // Test Invalid Account Type (if supported)
        // Uncomment if you handle unknown account types
        // assertThrows(IllegalArgumentException.class, () -> resolver.resolve(null));
    }

    @Test
    void testStrategyConsistencyForSameAccountType() {
        InterestStrategyResolver resolver = new InterestStrategyResolver();

        InterestStrategy strategy1 = resolver.resolve(AccountType.SAVINGS);
        InterestStrategy strategy2 = resolver.resolve(AccountType.SAVINGS);

        // They should be either the same instance or logically equivalent
        double interest1 = strategy1.calculateInterest(1500.0);
        double interest2 = strategy2.calculateInterest(1500.0);

        assertEquals(interest1, interest2, 0.001, "Same account type should yield same interest");
    }

    @Test
    void testEdgeCases() {
        InterestStrategyResolver resolver = new InterestStrategyResolver();

        // Zero balance
        InterestStrategy savings = resolver.resolve(AccountType.SAVINGS);
        assertEquals(0.0, savings.calculateInterest(0.0), 0.001, "Zero balance should yield zero interest");

        // Negative balance for loan (already tested)
        InterestStrategy loan = resolver.resolve(AccountType.LOAN);
        assertEquals(500.0, loan.calculateInterest(-10000.0), 0.001);

        // Large amount
        double largeAmount = 1_000_000.0;
        double largeSavingsInterest = savings.calculateInterest(largeAmount);
        assertEquals(20000.0, largeSavingsInterest, 0.001, "Interest for 1,000,000 should be 20,000");
    }
}