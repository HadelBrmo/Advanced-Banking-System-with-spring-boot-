package com.example.Advances.Banking.System.patterns.structural.adapter;

import com.example.Advances.Banking.System.patterns.structural.adapter.models.*;

public class PayPalAdapter extends PaymentGatewayAdapter {
    private final String paypalApiKey;
    private final String paypalSecret;
    private final MerchantCredentials credentials;

    public PayPalAdapter() {
        super(new ExternalPaymentService("PayPal"), "PayPal Gateway");
        this.paypalApiKey = "paypal_live_ak_test123";
        this.paypalSecret = "paypal_live_sk_test456";
        this.credentials = new MerchantCredentials(paypalApiKey, paypalSecret);
    }

    @Override
    public PaymentRequest createPaymentRequest(
            double amount, String currency, String customerId) {

        return new PaymentRequest(amount, currency, customerId + "@customer.bank");
    }

    @Override
    public MerchantCredentials getCredentials() {
        return credentials;
    }

    @Override
    public String convertStatus(String externalStatus) {
        switch (externalStatus) {
            case "COMPLETED": return "SUCCESS";
            case "PENDING": return "PENDING";
            case "FAILED": return "FAILED";
            default: return "UNKNOWN";
        }
    }

    public boolean authorizePayment(double amount, String customerEmail) {
        System.out.println("🔐 [PayPal] تفويض الدفع: " + amount + " للعميل: " + customerEmail);
        return true;
    }
}