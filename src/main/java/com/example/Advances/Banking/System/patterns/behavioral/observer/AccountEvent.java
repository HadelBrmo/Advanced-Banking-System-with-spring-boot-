package com.example.Advances.Banking.System.patterns.behavioral.observer;


import java.util.Date;

public class AccountEvent {

    private final String eventType;
    private final String accountNumber;
    private final double amount;
    private final Date timestamp;
    private final String description;

    public AccountEvent(String eventType, String accountNumber, double amount, String description) {
        this.eventType = eventType;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.timestamp = new Date();
        this.description = description;
    }


    public String getEventType() { return eventType; }
    public String getAccountNumber() { return accountNumber; }
    public double getAmount() { return amount; }
    public Date getTimestamp() { return timestamp; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return String.format("[%s] %s - Account: %s, Amount: %.2f",
                timestamp, description, accountNumber, amount);
    }
}
