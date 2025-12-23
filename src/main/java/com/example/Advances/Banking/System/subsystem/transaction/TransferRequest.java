package com.example.Advances.Banking.System.subsystem.transaction;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransferRequest {
    private String fromAccount;
    private String toAccount;
    private double amount;
    private String currency = "USD";
    private String description;


    public TransferRequest() {}

    public TransferRequest(String fromAccount, String toAccount, double amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

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
