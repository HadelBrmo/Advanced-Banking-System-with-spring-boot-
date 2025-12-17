package com.example.Advances.Banking.System.nfr.testing.strategy;

import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.patterns.behavioral.strategy.InterestStrategy;
import com.example.Advances.Banking.System.patterns.behavioral.strategy.InterestStrategyResolver;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InterestStrategyResolverTest {

    @Test
    void resolvesSavingsStrategy() {
        InterestStrategyResolver resolver = new InterestStrategyResolver();
        InterestStrategy strategy = resolver.resolve(AccountType.SAVINGS);
        assertNotNull(strategy);
        assertEquals(20.0, strategy.calculateInterest(1000.0), 0.001);
    }

    @Test
    void resolvesLoanStrategy() {
        InterestStrategyResolver resolver = new InterestStrategyResolver();
        InterestStrategy strategy = resolver.resolve(AccountType.LOAN);
        assertNotNull(strategy);
        assertEquals(500.0, strategy.calculateInterest(-10000.0), 0.001);
    }
}
