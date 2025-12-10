package com.example.Advances.Banking.System.patterns.structural.adapter;


public interface PaymentGateway {

    String processPayment(double amount, String currency, String customerId);


    String checkPaymentStatus(String transactionId);


    boolean refundPayment(String transactionId);


    String getGatewayName();
}
