package com.example.Advances.Banking.System.core.enums;

public enum AccountType {
    SAVINGS("Savings Account", 0.02),
    CHECKING("Checking Account", 0.01),
    LOAN("Loan Account", 0.05),
    INVESTMENT("Investment Account", 0.03),
    //التوسعة
    STUDENT("Student Account", 0.03),
    BUSINESS("Business Account", 0.015),
    CRYPTO("Cryptocurrency Account", 0.0);

    private final String description;
    private final double defaultInterestRate;

    AccountType(String description, double defaultInterestRate) {
        this.description = description;
        this.defaultInterestRate = defaultInterestRate;
    }

    public String getDescription() {
        return description;
    }

    public double getDefaultInterestRate() {
        return defaultInterestRate;
    }
}