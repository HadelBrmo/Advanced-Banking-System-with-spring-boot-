package com.example.Advances.Banking.System.patterns.structural.adapter;

import com.example.Advances.Banking.System.patterns.structural.adapter.models.*;

public abstract class PaymentGatewayAdapter implements PaymentGateway {
    public final ExternalPaymentService externalService;
    protected final String gatewayName;

    public PaymentGatewayAdapter(ExternalPaymentService externalService, String gatewayName) {
        this.externalService = externalService;
        this.gatewayName = gatewayName;
    }

    @Override
    public String processPayment(double amount, String currency, String customerId) {
        // التحقق من المدخلات
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (currency == null || currency.trim().isEmpty()) throw new IllegalArgumentException("Currency required");
        if (customerId == null || customerId.trim().isEmpty()) throw new IllegalArgumentException("Customer ID required");

        PaymentRequest request = createPaymentRequest(amount, currency, customerId);
        MerchantCredentials credentials = getCredentials();

        PaymentResponse response = externalService.makePayment(request, credentials);

        if (response.isSuccess()) {
            System.out.println("✅ [" + gatewayName + "] تمت معالجة الدفع بنجاح");
            return response.getTransactionId();
        } else {
            System.out.println("❌ [" + gatewayName + "] فشل معالجة الدفع: " + response.getMessage());
            throw new RuntimeException("Payment failed: " + response.getMessage());
        }
    }

    @Override
    public String checkPaymentStatus(String transactionId) {
        if (transactionId == null || transactionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Transaction ID required");
        }

        TransactionStatus status = externalService.verifyTransaction(transactionId);
        return convertStatus(status.getStatus());
    }

    @Override
    public boolean refundPayment(String transactionId) {
        if (transactionId == null || transactionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Transaction ID required");
        }

        return externalService.reverseTransaction(transactionId, "Customer requested refund");
    }

    @Override
    public String getGatewayName() {
        return gatewayName;
    }

    public abstract PaymentRequest createPaymentRequest(
            double amount, String currency, String customerId);

    public abstract MerchantCredentials getCredentials();

    public abstract String convertStatus(String externalStatus);
}