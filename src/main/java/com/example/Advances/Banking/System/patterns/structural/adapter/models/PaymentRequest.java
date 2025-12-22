package com.example.Advances.Banking.System.patterns.structural.adapter.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

}