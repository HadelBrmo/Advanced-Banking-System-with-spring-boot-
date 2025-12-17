package com.example.Advances.Banking.System.patterns.structural.adapter;

import com.example.Advances.Banking.System.patterns.structural.adapter.models.*;

public class StripeAdapter extends PaymentGatewayAdapter {

    private final String stripeApiKey;
    private final String stripePublishableKey;
    private final MerchantCredentials credentials;

    public StripeAdapter() {
        super(new ExternalPaymentService("Stripe"), "Stripe Gateway");
        this.stripeApiKey = "sk_live_test123";
        this.stripePublishableKey = "pk_live_test456";
        this.credentials = new MerchantCredentials(stripeApiKey, stripePublishableKey);
    }

    @Override
    public PaymentRequest createPaymentRequest(
            double amount, String currency, String customerId) {

        PaymentRequest request = new PaymentRequest();
        request.setPaymentAmount(amount);
        request.setPaymentCurrency(currency);
        request.setCustomerEmail("cust_" + customerId + "@stripe.bank");

        return request;
    }

    @Override
    public MerchantCredentials getCredentials() {
        return credentials;
    }

    @Override
    public String convertStatus(String externalStatus) {
        // حل المشكلة: تحقق من null قبل استخدام switch
        if (externalStatus == null) {
            return "UNKNOWN";
        }

        switch (externalStatus) {
            case "COMPLETED":
            case "succeeded": return "SUCCESS";
            case "PENDING":
            case "processing": return "PENDING";
            case "FAILED":
            case "requires_payment_method": return "FAILED";
            default: return "UNKNOWN";
        }
    }

    public String createPaymentIntent(double amount, String currency) {
        System.out.println("🎯 [Stripe] إنشاء نية دفع: " + amount + " " + currency);
        return "pi_" + System.currentTimeMillis();
    }

    public boolean capturePayment(String paymentIntentId, double amount) {
        System.out.println("💰 [Stripe] تحويل نية الدفع إلى دفعة فعلية: " + paymentIntentId);
        System.out.println("   المبلغ: " + amount);
        return Math.random() > 0.1;
    }
}