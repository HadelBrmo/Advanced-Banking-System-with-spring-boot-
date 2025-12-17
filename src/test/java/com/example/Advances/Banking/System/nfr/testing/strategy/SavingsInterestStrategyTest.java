package com.example.Advances.Banking.System.nfr.testing.strategy;

import com.example.Advances.Banking.System.patterns.behavioral.strategy.SavingsInterestStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SavingsInterestStrategyTest {

    @Test
    void calculatesCorrectInterest() {
        SavingsInterestStrategy strategy = new SavingsInterestStrategy();
        double result = strategy.calculateInterest(1000.0);
        assertEquals(20.0, result, 0.001); // 2% من 1000
    }
}
