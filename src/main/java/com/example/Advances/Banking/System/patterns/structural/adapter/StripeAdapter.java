package com.example.Advances.Banking.System.patterns.structural.adapter;


public class StripeAdapter extends PaymentGatewayAdapter {

    private final String stripeApiKey;
    private final String stripePublishableKey;

    public StripeAdapter() {
        super(new ExternalPaymentService("Stripe"), "Stripe Gateway");
        this.stripeApiKey = "sk_live_test123";
        this.stripePublishableKey = "pk_live_test456";
    }

    @Override
    protected ExternalPaymentService.PaymentRequest createPaymentRequest(
            double amount, String currency, String customerId) {

        ExternalPaymentService.PaymentRequest request = new ExternalPaymentService.PaymentRequest();
        request.setPaymentAmount(amount);
        request.setPaymentCurrency(currency);
        request.setCustomerEmail("cust_" + customerId + "@stripe.bank");

        return request;
    }

    @Override
    protected ExternalPaymentService.MerchantCredentials getCredentials() {
        return new ExternalPaymentService.MerchantCredentials(stripeApiKey, stripePublishableKey);
    }

    @Override
    protected String convertStatus(String externalStatus) {

        switch (externalStatus) {
            case "succeeded": return "SUCCESS";
            case "processing": return "PENDING";
            case "requires_payment_method": return "FAILED";
            default: return "UNKNOWN";
        }
    }

    public String createPaymentIntent(double amount, String currency) {
        System.out.println("🎯 [Stripe] إنشاء نية دفع: " + amount + " " + currency);
        return "pi_" + System.currentTimeMillis();
    }
}
