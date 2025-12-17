package com.example.Advances.Banking.System.patterns.behavioral.strategy;

import org.springframework.stereotype.Service;

@Service
public class InterestCalculatorService {
    public double calculate(InterestStrategy strategy, double balance) {
        return strategy.calculateInterest(balance);
    }
}