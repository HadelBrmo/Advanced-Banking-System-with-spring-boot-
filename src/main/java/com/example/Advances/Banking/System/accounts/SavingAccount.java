package com.example.Advances.Banking.System.accounts;

public class SavingAccount extends Account{

    private double interestRate=0.05;

    public SavingAccount() {
       super("savings",0.0);
    }

    @Override
    public double calculateInterest() {
        return getBalance()+interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
