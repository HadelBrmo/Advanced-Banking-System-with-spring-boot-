package com.example.Advances.Banking.System.nfr.testing.strategy;

import com.example.Advances.Banking.System.patterns.behavioral.strategy.LoanInterestStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoanInterestStrategyTest {

    @Test
    void calculatesLoanInterest() {
        LoanInterestStrategy strategy = new LoanInterestStrategy();
        double result = strategy.calculateInterest(-10000.0); // قرض
        assertEquals(500.0, result, 0.001); // 5% من 10000
    }
}