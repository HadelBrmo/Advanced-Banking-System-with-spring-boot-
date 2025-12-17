package com.example.Advances.Banking.System.patterns.behavioral.strategy;

public class CheckingInterestStrategy implements InterestStrategy {
    @Override
    public double calculateInterest(double balance) {
        return 0.0;
    }
}