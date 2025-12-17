package com.example.Advances.Banking.System.nfr.testing.strategy;

import com.example.Advances.Banking.System.patterns.behavioral.strategy.SimpleInterestStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleInterestStrategyTest {

    @Test
    void calculatesSimpleInterest() {
        SimpleInterestStrategy strategy = new SimpleInterestStrategy(0.01);
        double result = strategy.calculateInterest(2000.0);
        assertEquals(20.0, result, 0.001);
    }
}