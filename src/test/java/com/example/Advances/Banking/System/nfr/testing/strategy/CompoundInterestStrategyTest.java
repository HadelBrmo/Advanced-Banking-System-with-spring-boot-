package com.example.Advances.Banking.System.nfr.testing.strategy;

import com.example.Advances.Banking.System.patterns.behavioral.strategy.CompoundInterestStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompoundInterestStrategyTest {

    @Test
    void calculatesCompoundInterest() {
        CompoundInterestStrategy strategy = new CompoundInterestStrategy();
        double result = strategy.calculateInterest(1000.0);
        assertTrue(result > 0);
    }
}