package com.example.Advances.Banking.System.patterns.structural.adapter;


public abstract class PaymentGatewayAdapter implements PaymentGateway {

    protected final ExternalPaymentService externalService;
    protected final String gatewayName;

    public PaymentGatewayAdapter(ExternalPaymentService externalService, String gatewayName) {
        this.externalService = externalService;
        this.gatewayName = gatewayName;
    }

    @Override
    public String processPayment(double amount, String currency, String customerId) {

        ExternalPaymentService.PaymentRequest request = createPaymentRequest(amount, currency, customerId);
        ExternalPaymentService.MerchantCredentials credentials = getCredentials();

        ExternalPaymentService.ExternalPaymentResponse response =
                externalService.makePayment(request, credentials);

        if (response.isSuccess()) {
            System.out.println("✅ [" + gatewayName + "] تمت معالجة الدفع بنجاح");
            return response.getTransactionId();
        } else {
            System.out.println("❌ [" + gatewayName + "] فشل معالجة الدفع");
            throw new RuntimeException("Payment failed: " + response.getMessage());
        }
    }

    @Override
    public String checkPaymentStatus(String transactionId) {

        ExternalPaymentService.TransactionStatus status =
                externalService.verifyTransaction(transactionId);
        return convertStatus(status.getStatus());
    }

    @Override
    public boolean refundPayment(String transactionId) {

        return externalService.reverseTransaction(transactionId, "Customer requested refund");
    }

    @Override
    public String getGatewayName() {
        return gatewayName;
    }


    protected abstract ExternalPaymentService.PaymentRequest createPaymentRequest(
            double amount, String currency, String customerId);

    protected abstract ExternalPaymentService.MerchantCredentials getCredentials();

    protected abstract String convertStatus(String externalStatus);
}
