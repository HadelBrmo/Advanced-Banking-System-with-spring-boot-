package com.example.Advances.Banking.System.nfr.testing.adapter;

import com.example.Advances.Banking.System.patterns.structural.adapter.*;
import com.example.Advances.Banking.System.patterns.structural.adapter.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

@DisplayName("ExternalPaymentService Tests")
class ExternalPaymentServiceTest {

    private ExternalPaymentService paypalService;
    private ExternalPaymentService stripeService;

    @BeforeEach
    void setUp() {
        paypalService = new ExternalPaymentService("PayPal");
        stripeService = new ExternalPaymentService("Stripe");
    }

    @Test
    @DisplayName("Constructor should set service name")
    void constructor_ShouldSetServiceName() {
        ExternalPaymentService service = new ExternalPaymentService("Test Service");
        assertNotNull(service);
    }

    @Test
    @DisplayName("Make payment should return success or failure")
    void makePayment_ShouldReturnResponse() {
        // Arrange
        PaymentRequest request = new PaymentRequest(100.0, "USD", "test@example.com");
        MerchantCredentials credentials = new MerchantCredentials("key", "secret");

        // Act
        PaymentResponse response = paypalService.makePayment(request, credentials);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getMessage());

        if (response.isSuccess()) {
            assertNotNull(response.getTransactionId());
            assertTrue(response.getTransactionId().startsWith("EXT-"));
            assertTrue(response.getTransactionId().contains("PayPal"));
        } else {
            assertNull(response.getTransactionId());
            assertTrue(response.getMessage().contains("failed"));
        }
    }

    @Test
    @DisplayName("Verify transaction should return random status")
    void verifyTransaction_ShouldReturnRandomStatus() {
        // Arrange
        String transactionId = "EXT-123456789-PayPal";

        // Act
        TransactionStatus status = paypalService.verifyTransaction(transactionId);

        // Assert
        assertNotNull(status);
        assertNotNull(status.getStatus());
        assertTrue(status.getTimestamp() > 0);
        assertEquals(transactionId, status.getExternalTransactionId());

        String[] validStatuses = {"COMPLETED", "PENDING", "FAILED"};
        boolean isValid = false;
        for (String validStatus : validStatuses) {
            if (validStatus.equals(status.getStatus())) {
                isValid = true;
                break;
            }
        }
        assertTrue(isValid, "Status should be one of: COMPLETED, PENDING, FAILED");
    }

    @Test
    @DisplayName("Reverse transaction should return boolean")
    void reverseTransaction_ShouldReturnBoolean() {
        // Arrange
        String transactionId = "EXT-987654321-PayPal";
        String reason = "Test reversal";

        // Act
        boolean result = paypalService.reverseTransaction(transactionId, reason);

        // Assert
        assertNotNull(Boolean.valueOf(result));
    }

    @Test
    @DisplayName("Different services should work independently")
    void differentServices_ShouldWorkIndependently() {
        // Test PayPal service
        PaymentRequest request1 = new PaymentRequest(50.0, "USD", "paypal@test.com");
        MerchantCredentials creds1 = new MerchantCredentials("paypal_key", "paypal_secret");
        PaymentResponse resp1 = paypalService.makePayment(request1, creds1);
        assertNotNull(resp1);

        PaymentRequest request2 = new PaymentRequest(75.0, "EUR", "stripe@test.com");
        MerchantCredentials creds2 = new MerchantCredentials("stripe_key", "stripe_secret");
        PaymentResponse resp2 = stripeService.makePayment(request2, creds2);
        assertNotNull(resp2);

        assertNotSame(resp1, resp2);
    }

    @Test
    @DisplayName("Payment success rate should be around 80%")
    void paymentSuccessRate_ShouldBeAround80Percent() {
        int totalAttempts = 1000;
        int successfulPayments = 0;

        PaymentRequest request = new PaymentRequest(10.0, "USD", "stat@test.com");
        MerchantCredentials credentials = new MerchantCredentials("key", "secret");

        for (int i = 0; i < totalAttempts; i++) {
            PaymentResponse response = paypalService.makePayment(request, credentials);
            if (response.isSuccess()) {
                successfulPayments++;
            }
        }

        double successRate = (successfulPayments * 100.0) / totalAttempts;
        System.out.println("📊 Payment Success Rate: " + String.format("%.1f", successRate) + "%");


        assertTrue(successRate > 75 && successRate < 85,
                "Success rate should be around 80% (±5%). Actual: " + successRate + "%");
    }

    @Test
    @DisplayName("Transaction status distribution should be even")
    void transactionStatus_DistributionShouldBeEven() {
        int totalChecks = 999; // Multiple of 3 for even distribution
        Map<String, Integer> statusCount = new HashMap<>();

        for (int i = 0; i < totalChecks; i++) {
            TransactionStatus status = paypalService.verifyTransaction("TX-" + i);
            statusCount.put(status.getStatus(),
                    statusCount.getOrDefault(status.getStatus(), 0) + 1);
        }

        assertEquals(3, statusCount.size(), "Should have exactly 3 status types");
        assertTrue(statusCount.containsKey("COMPLETED"));
        assertTrue(statusCount.containsKey("PENDING"));
        assertTrue(statusCount.containsKey("FAILED"));

        System.out.println("\n📊 Transaction Status Distribution:");
        for (Map.Entry<String, Integer> entry : statusCount.entrySet()) {
            double percentage = entry.getValue() * 100.0 / totalChecks;
            System.out.println("  " + entry.getKey() + ": " + entry.getValue() +
                    " (" + String.format("%.1f", percentage) + "%)");

            assertTrue(percentage > 23 && percentage < 43,
                    "Each status should appear around 33% (±10%). Actual: " +
                            entry.getKey() + " = " + String.format("%.1f", percentage) + "%");
        }
    }

    @Test
    @DisplayName("Reverse transaction success rate should be around 70%")
    void reverseTransactionSuccessRate_ShouldBeAround70Percent() {
        int totalAttempts = 1000;
        int successfulReversals = 0;

        for (int i = 0; i < totalAttempts; i++) {
            boolean result = paypalService.reverseTransaction("TX-" + i, "Test reason");
            if (result) {
                successfulReversals++;
            }
        }

        double successRate = (successfulReversals * 100.0) / totalAttempts;
        System.out.println("📊 Reverse Transaction Success Rate: " +
                String.format("%.1f", successRate) + "%");


        assertTrue(successRate > 65 && successRate < 75,
                "Reverse success rate should be around 70% (±5%). Actual: " + successRate + "%");
    }

    @ParameterizedTest
    @CsvSource({
            "100.0, USD, customer1@test.com",
            "0.01, EUR, customer2@test.com",
            "999999.99, GBP, customer3@test.com",
            "1.0, JPY, customer4@test.com"
    })
    @DisplayName("Make payment with different request values")
    void makePayment_WithDifferentRequestValues(double amount, String currency, String email) {
        PaymentRequest request = new PaymentRequest(amount, currency, email);
        MerchantCredentials credentials = new MerchantCredentials("key", "secret");

        PaymentResponse response = paypalService.makePayment(request, credentials);

        assertNotNull(response);
        assertNotNull(response.getMessage());

        if (response.isSuccess()) {
            assertNotNull(response.getTransactionId());
            assertTrue(response.getTransactionId().contains("PayPal"));
        }
    }

    @Test
    @DisplayName("Credentials toString should mask sensitive data")
    void credentialsToString_ShouldMaskSensitiveData() {
        MerchantCredentials credentials = new MerchantCredentials(
                "live_sk_test_1234567890abcdef",
                "very_secret_key_9876543210"
        );

        String toString = credentials.toString();
        System.out.println("🔐 Masked Credentials: " + toString);

        // Should contain masked data
        assertTrue(toString.contains("***"));
        // Should NOT contain full keys
        assertFalse(toString.contains("live_sk_test_1234567890abcdef"));
        assertFalse(toString.contains("very_secret_key_9876543210"));
        // Should show last 4 characters only
        assertTrue(toString.contains("cdef"));
        assertTrue(toString.contains("3210"));
    }

    @Test
    @DisplayName("Service names should be included in transaction IDs")
    void serviceNames_ShouldBeIncludedInTransactionIDs() {
        PaymentRequest request = new PaymentRequest(50.0, "USD", "test@test.com");
        MerchantCredentials credentials = new MerchantCredentials("key", "secret");

        PaymentResponse paypalResponse = paypalService.makePayment(request, credentials);
        if (paypalResponse.isSuccess()) {
            assertTrue(paypalResponse.getTransactionId().contains("PayPal"));
        }

        PaymentResponse stripeResponse = stripeService.makePayment(request, credentials);
        if (stripeResponse.isSuccess()) {
            assertTrue(stripeResponse.getTransactionId().contains("Stripe"));
        }
    }

    @Test
    @DisplayName("Verify transaction with null ID should work")
    void verifyTransaction_WithNullId() {
        TransactionStatus status = paypalService.verifyTransaction(null);
        assertNotNull(status);
        assertNotNull(status.getStatus());
        assertNull(status.getExternalTransactionId());
    }

    @Test
    @DisplayName("Edge cases for payment amounts")
    void edgeCases_ForPaymentAmounts() {
        PaymentRequest smallAmount = new PaymentRequest(0.01, "USD", "small@test.com");
        MerchantCredentials creds = new MerchantCredentials("key", "secret");

        PaymentResponse response1 = paypalService.makePayment(smallAmount, creds);
        assertNotNull(response1);

        PaymentRequest zeroAmount = new PaymentRequest(0.0, "USD", "zero@test.com");
        PaymentResponse response2 = paypalService.makePayment(zeroAmount, creds);
        assertNotNull(response2);

        PaymentRequest largeAmount = new PaymentRequest(9999999.99, "USD", "large@test.com");
        PaymentResponse response3 = paypalService.makePayment(largeAmount, creds);
        assertNotNull(response3);
    }
}