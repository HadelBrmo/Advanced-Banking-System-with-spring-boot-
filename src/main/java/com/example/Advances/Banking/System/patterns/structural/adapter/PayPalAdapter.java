package com.example.Advances.Banking.System.patterns.structural.adapter;


public class PayPalAdapter extends PaymentGatewayAdapter {

    private final String paypalApiKey;
    private final String paypalSecret;

    public PayPalAdapter() {
        super(new ExternalPaymentService("PayPal"), "PayPal Gateway");
        this.paypalApiKey = "paypal_live_ak_test123";
        this.paypalSecret = "paypal_live_sk_test456";
    }

    @Override
    protected ExternalPaymentService.PaymentRequest createPaymentRequest(
            double amount, String currency, String customerId) {

        ExternalPaymentService.PaymentRequest request = new ExternalPaymentService.PaymentRequest();
        request.setPaymentAmount(amount);
        request.setPaymentCurrency(currency);
        request.setCustomerEmail(customerId + "@customer.bank");

        return request;
    }

    @Override
    protected ExternalPaymentService.MerchantCredentials getCredentials() {
        return new ExternalPaymentService.MerchantCredentials(paypalApiKey, paypalSecret);
    }

    @Override
    protected String convertStatus(String externalStatus) {
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
