package com.example.Advances.Banking.System.core.enums;


public enum TransactionType {
    DEPOSIT("Deposit"),
    WITHDRAWAL("Withdrawal"),
    TRANSFER("Transfer"),
    PAYMENT("Payment"),
    FEE("Fee");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
