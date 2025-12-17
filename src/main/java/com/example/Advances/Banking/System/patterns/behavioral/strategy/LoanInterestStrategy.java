package com.example.Advances.Banking.System.patterns.behavioral.strategy;

public class LoanInterestStrategy implements InterestStrategy {
    private static final double RATE = 0.05;

    @Override
    public double calculateInterest(double balance) {
        return Math.abs(balance) * RATE;
    }
}