package com.example.Advances.Banking.System.subsystem.transaction;


public class TransferRequest {
    private String fromAccount;
    private String toAccount;
    private double amount;
    private String currency = "USD";
    private String description;

    // Constructors
    public TransferRequest() {}

    public TransferRequest(String fromAccount, String toAccount, double amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    // Getters and Setters يدوياً
    public String getFromAccount() { return fromAccount; }
    public void setFromAccount(String fromAccount) { this.fromAccount = fromAccount; }

    public String getToAccount() { return toAccount; }
    public void setToAccount(String toAccount) { this.toAccount = toAccount; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "TransferRequest{" +
               "fromAccount='" + fromAccount + '\'' +
               ", toAccount='" + toAccount + '\'' +
               ", amount=" + amount +
               ", currency='" + currency + '\'' +
               ", description='" + description + '\'' +
               '}';
    }
}
