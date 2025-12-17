package com.example.Advances.Banking.System.patterns.behavioral.strategy;

public class SimpleInterestStrategy implements InterestStrategy {
    private final double rate;

    public SimpleInterestStrategy(double rate) {
        this.rate = rate;
    }

    @Override
    public double calculateInterest(double balance) {
        return balance * rate;
    }
}