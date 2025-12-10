package com.example.Advances.Banking.System.patterns.structural.adapter;


public class ExternalPaymentService {

    private final String serviceName;

    public ExternalPaymentService(String serviceName) {
        this.serviceName = serviceName;
    }

    public ExternalPaymentResponse makePayment(
            PaymentRequest request,
            MerchantCredentials credentials) {

        System.out.println("[" + serviceName + "] معالجة الدفع...");
        System.out.println("  المبلغ: " + request.getPaymentAmount());
        System.out.println("  العملة: " + request.getPaymentCurrency());
        System.out.println("  العميل: " + request.getCustomerEmail());

        // محاكاة اتصال بالنظام الخارجي
        boolean success = Math.random() > 0.2; // 80% نجاح

        if (success) {
            String externalId = "EXT-" + System.currentTimeMillis();
            return new ExternalPaymentResponse(true, externalId, "Payment processed successfully");
        } else {
            return new ExternalPaymentResponse(false, null, "Payment failed");
        }
    }

    public TransactionStatus verifyTransaction(String externalTransactionId) {
        System.out.println("[" + serviceName + "] التحقق من المعاملة: " + externalTransactionId);

        String[] statuses = {"COMPLETED", "PENDING", "FAILED"};
        String randomStatus = statuses[(int)(Math.random() * statuses.length)];

        return new TransactionStatus(randomStatus, System.currentTimeMillis());
    }


    public boolean reverseTransaction(String externalTransactionId, String reason) {
        System.out.println("[" + serviceName + "] إلغاء المعاملة: " + externalTransactionId);
        System.out.println("  السبب: " + reason);

        return Math.random() > 0.3; // 70% نجاح
    }



    public static class PaymentRequest {
        private double paymentAmount;
        private String paymentCurrency;
        private String customerEmail;

        // Getters and Setters
        public double getPaymentAmount() { return paymentAmount; }
        public void setPaymentAmount(double paymentAmount) { this.paymentAmount = paymentAmount; }

        public String getPaymentCurrency() { return paymentCurrency; }
        public void setPaymentCurrency(String paymentCurrency) { this.paymentCurrency = paymentCurrency; }

        public String getCustomerEmail() { return customerEmail; }
        public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    }

    public static class MerchantCredentials {
        private String apiKey;
        private String secretKey;

        public MerchantCredentials(String apiKey, String secretKey) {
            this.apiKey = apiKey;
            this.secretKey = secretKey;
        }

        public String getApiKey() { return apiKey; }
        public String getSecretKey() { return secretKey; }
    }

    public static class ExternalPaymentResponse {
        private boolean success;
        private String transactionId;
        private String message;

        public ExternalPaymentResponse(boolean success, String transactionId, String message) {
            this.success = success;
            this.transactionId = transactionId;
            this.message = message;
        }

        public boolean isSuccess() { return success; }
        public String getTransactionId() { return transactionId; }
        public String getMessage() { return message; }
    }

    public static class TransactionStatus {
        private String status;
        private long timestamp;

        public TransactionStatus(String status, long timestamp) {
            this.status = status;
            this.timestamp = timestamp;
        }

        public String getStatus() { return status; }
        public long getTimestamp() { return timestamp; }
    }
}
