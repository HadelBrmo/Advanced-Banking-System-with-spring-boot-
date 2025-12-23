package com.example.Advances.Banking.System.patterns.behavioral.observer;


import lombok.Getter;

import java.util.Date;

@Getter
public class AccountEvent {

    private final String eventType;
    private final String accountNumber;
    private final double amount;
    private final Date timestamp;
    private final String description;

    public AccountEvent(String eventType, String accountNumber, double amount, String description, String s) {
        this.eventType = eventType;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.timestamp = new Date();
        this.description = description;
    }


    @Override
    public String toString() {
        return String.format("[%s] %s - Account: %s, Amount: %.2f",
                timestamp, description, accountNumber, amount);
    }
}
