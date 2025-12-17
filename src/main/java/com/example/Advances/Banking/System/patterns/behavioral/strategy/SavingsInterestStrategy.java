package com.example.Advances.Banking.System.patterns.behavioral.strategy;

public class SavingsInterestStrategy implements InterestStrategy {
    private static final double RATE = 0.02;

    @Override
    public double calculateInterest(double balance) {
        return balance * RATE;
    }
}