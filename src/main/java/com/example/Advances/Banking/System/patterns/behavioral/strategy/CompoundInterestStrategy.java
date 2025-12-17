package com.example.Advances.Banking.System.patterns.behavioral.strategy;

public class CompoundInterestStrategy implements InterestStrategy {
    private static final double RATE = 0.03;
    private static final int PERIODS = 12;

    @Override
    public double calculateInterest(double balance) {
        return balance * Math.pow(1 + RATE, PERIODS) - balance;
    }
}
