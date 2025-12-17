package com.example.Advances.Banking.System.nfr.testing.strategy;


import com.example.Advances.Banking.System.patterns.behavioral.strategy.CheckingInterestStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CheckingInterestStrategyTest {

    @Test
    void returnsZeroInterest() {
        CheckingInterestStrategy strategy = new CheckingInterestStrategy();
        double result = strategy.calculateInterest(1000.0);
        assertEquals(0.0, result, 0.001);
    }
}
