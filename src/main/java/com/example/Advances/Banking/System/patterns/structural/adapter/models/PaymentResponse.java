package com.example.Advances.Banking.System.patterns.structural.adapter.models;

import lombok.Getter;

@Getter
public class PaymentResponse {
    private final boolean success;
    private final String transactionId;
    private final String message;

    public PaymentResponse(boolean success, String transactionId, String message) {
        this.success = success;
        this.transactionId = transactionId;
        this.message = message;
    }


    public static PaymentResponse success(String transactionId) {
        return new PaymentResponse(true, transactionId, "Payment processed successfully");
    }

    public static PaymentResponse failure(String message) {
        return new PaymentResponse(false, null, message);
    }

    public static PaymentResponse failure(String transactionId, String message) {
        return new PaymentResponse(false, transactionId, message);
    }
}