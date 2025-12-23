package com.example.Advances.Banking.System.patterns.behavioral.strategy;

public interface InterestStrategy {
    //حساب الفائدة
    double calculateInterest(double balance);
}