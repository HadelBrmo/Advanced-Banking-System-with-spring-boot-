package com.example.Advances.Banking.System.patterns.structural.adapter.models;

public class PaymentRequest {
    private double paymentAmount;
    private String paymentCurrency;
    private String customerEmail;


    public PaymentRequest() {}

    public PaymentRequest(double paymentAmount, String paymentCurrency, String customerEmail) {
        this.paymentAmount = paymentAmount;
        this.paymentCurrency = paymentCurrency;
        this.customerEmail = customerEmail;
    }

    // Getters and Setters
    public double getPaymentAmount() { return paymentAmount; }
    public void setPaymentAmount(double paymentAmount) { this.paymentAmount = paymentAmount; }

    public String getPaymentCurrency() { return paymentCurrency; }
    public void setPaymentCurrency(String paymentCurrency) {
        this.paymentCurrency = paymentCurrency;
    }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}