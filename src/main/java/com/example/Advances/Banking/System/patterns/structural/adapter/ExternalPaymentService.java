package com.example.Advances.Banking.System.patterns.structural.adapter;

import com.example.Advances.Banking.System.patterns.structural.adapter.models.*;

public class ExternalPaymentService {
    public final String serviceName;

    public ExternalPaymentService(String serviceName) {
        this.serviceName = serviceName;
    }

    public PaymentResponse makePayment(
            PaymentRequest request,
            MerchantCredentials credentials) {

        System.out.println("[" + serviceName + "] معالجة الدفع...");
        System.out.println("  المبلغ: " + request.getPaymentAmount());
        System.out.println("  العملة: " + request.getPaymentCurrency());
        System.out.println("  العميل: " + request.getCustomerEmail());
        System.out.println("  الاعتماد: " + credentials);


        boolean success = Math.random() > 0.2;

        if (success) {
            String externalId = "EXT-" + System.currentTimeMillis() + "-" + serviceName;
            return PaymentResponse.success(externalId);
        } else {
            return PaymentResponse.failure("Payment failed - External service error");
        }
    }

    public TransactionStatus verifyTransaction(String externalTransactionId) {
        System.out.println("[" + serviceName + "] التحقق من المعاملة: " + externalTransactionId);

        String[] statuses = {"COMPLETED", "PENDING", "FAILED"};
        String randomStatus = statuses[(int)(Math.random() * statuses.length)];

        return new TransactionStatus(randomStatus, System.currentTimeMillis(), externalTransactionId);
    }

    public boolean reverseTransaction(String externalTransactionId, String reason) {
        System.out.println("[" + serviceName + "] إلغاء المعاملة: " + externalTransactionId);
        System.out.println("  السبب: " + reason);

        return Math.random() > 0.3;
    }
}