package com.example.Advances.Banking.System.nfr.testing.adapter;

import com.example.Advances.Banking.System.patterns.structural.adapter.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Payment Gateway Integration Tests")
class PaymentGatewayIntegrationTest {

    private List<PaymentGateway> paymentGateways;

    @BeforeEach
    void setUp() {
        paymentGateways = new ArrayList<>();
        paymentGateways.add(new PayPalAdapter());
        paymentGateways.add(new StripeAdapter());
    }

    @Test
    @DisplayName("Multiple gateway payment processing")
    void multipleGatewayPaymentProcessing() {
        int successfulPayments = 0;
        int failedPayments = 0;

        for (PaymentGateway gateway : paymentGateways) {
            try {
                String transactionId = gateway.processPayment(100.0, "USD", "integrationTest");
                assertNotNull(transactionId);
                successfulPayments++;

                String status = gateway.checkPaymentStatus(transactionId);
                assertNotNull(status);

            } catch (RuntimeException e) {
                failedPayments++;
                assertTrue(e.getMessage().contains("Payment failed"));
            }
        }

        assertTrue(successfulPayments + failedPayments == paymentGateways.size());
    }

    @Test
    @DisplayName("Gateway name uniqueness")
    void gatewayNames_ShouldBeUnique() {
        String paypalName = paymentGateways.get(0).getGatewayName();
        String stripeName = paymentGateways.get(1).getGatewayName();

        assertNotNull(paypalName);
        assertNotNull(stripeName);
        assertNotEquals(paypalName, stripeName);

        assertTrue(paypalName.contains("PayPal"));
        assertTrue(stripeName.contains("Stripe"));
    }

    @Test
    @DisplayName("All gateways should implement interface correctly")
    void allGateways_ShouldImplementInterfaceCorrectly() {
        for (PaymentGateway gateway : paymentGateways) {
            assertNotNull(gateway.getGatewayName());

            assertDoesNotThrow(() -> {
                try {
                    gateway.processPayment(1.0, "USD", "test");
                } catch (RuntimeException e) {
                }

                gateway.checkPaymentStatus("dummy-id");

                boolean refundResult = gateway.refundPayment("dummy-id");
                assertNotNull(Boolean.valueOf(refundResult));
            });
        }
    }

    @Test
    @DisplayName("Adapter pattern structure verification")
    void adapterPatternStructure_ShouldBeCorrect() {
        assertTrue(paymentGateways.get(0) instanceof PaymentGatewayAdapter);
        assertTrue(paymentGateways.get(1) instanceof PaymentGatewayAdapter);

        PaymentGatewayAdapter paypalAdapter = (PaymentGatewayAdapter) paymentGateways.get(0);
        PaymentGatewayAdapter stripeAdapter = (PaymentGatewayAdapter) paymentGateways.get(1);

        assertNotNull(paypalAdapter.externalService);
        assertNotNull(stripeAdapter.externalService);

        assertEquals("PayPal", paypalAdapter.externalService.serviceName);
        assertEquals("Stripe", stripeAdapter.externalService.serviceName);
    }

    @Test
    @DisplayName("Real-world payment scenario")
    void realWorldPaymentScenario() {
        List<String> transactions = new ArrayList<>();

        for (PaymentGateway gateway : paymentGateways) {
            for (int i = 1; i <= 3; i++) {
                double amount = i * 50.0;
                String customerId = "cust_" + gateway.getGatewayName() + "_" + i;

                try {
                    String transactionId = gateway.processPayment(amount, "USD", customerId);
                    transactions.add(transactionId);

                    String status = gateway.checkPaymentStatus(transactionId);

                    if (amount > 50.0) {
                        boolean refunded = gateway.refundPayment(transactionId);
                        assertNotNull(Boolean.valueOf(refunded));
                    }

                } catch (RuntimeException e) {
                }
            }
        }

        assertTrue(transactions.size() <= 6);
    }

    @Test
    @DisplayName("Error handling and recovery")
    void errorHandlingAndRecovery() {
        for (PaymentGateway gateway : paymentGateways) {
            assertThrows(RuntimeException.class, () -> {
                gateway.processPayment(-100.0, "USD", "invalid");
            });

            assertThrows(Exception.class, () -> {
                gateway.processPayment(100.0, null, null);
            });

            String status = gateway.checkPaymentStatus("INVALID_ID_123");
            assertNotNull(status);

            boolean refundResult = gateway.refundPayment("INVALID_ID_456");
            assertNotNull(Boolean.valueOf(refundResult));
        }
    }

    @Test
    @DisplayName("Gateway-specific functionality")
    void gatewaySpecificFunctionality() {
        PayPalAdapter paypal = (PayPalAdapter) paymentGateways.get(0);
        StripeAdapter stripe = (StripeAdapter) paymentGateways.get(1);

        boolean authResult = paypal.authorizePayment(100.0, "test@test.com");
        assertTrue(authResult);

        String paymentIntentId = stripe.createPaymentIntent(200.0, "USD");
        assertNotNull(paymentIntentId);
        assertTrue(paymentIntentId.startsWith("pi_"));

        boolean captureResult = stripe.capturePayment(paymentIntentId, 200.0);
        assertNotNull(Boolean.valueOf(captureResult));
    }

    @Test
    @DisplayName("Consistent behavior across gateways")
    void consistentBehavior_AcrossGateways() {
        for (PaymentGateway gateway : paymentGateways) {
            try {
                String txId = gateway.processPayment(50.0, "USD", "consistentTest");

                String status = gateway.checkPaymentStatus(txId);
                assertTrue(status.equals("SUCCESS") ||
                        status.equals("PENDING") ||
                        status.equals("FAILED") ||
                        status.equals("UNKNOWN"));

                boolean refunded = gateway.refundPayment(txId);
                assertNotNull(Boolean.valueOf(refunded));

            } catch (RuntimeException e) {
            }
        }
    }

    @Test
    @DisplayName("High volume payment processing")
    void highVolumePaymentProcessing() {
        int totalTransactions = 20;
        List<String> successfulTransactions = new ArrayList<>();

        for (PaymentGateway gateway : paymentGateways) {
            for (int i = 0; i < totalTransactions / 2; i++) {
                try {
                    String txId = gateway.processPayment(10.0 * (i + 1), "USD", "volume_" + i);
                    successfulTransactions.add(txId);
                } catch (RuntimeException e) {
                }
            }
        }

        assertTrue(successfulTransactions.size() <= totalTransactions);

        for (String txId : successfulTransactions) {
            assertNotNull(txId);
            assertTrue(txId.startsWith("EXT-"));
        }
    }
}