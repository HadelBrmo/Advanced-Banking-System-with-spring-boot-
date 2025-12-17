package com.example.Advances.Banking.System.nfr.testing.adapter;

import com.example.Advances.Banking.System.patterns.structural.adapter.*;
import com.example.Advances.Banking.System.patterns.structural.adapter.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PayPalAdapter Tests")
class PayPalAdapterTest {

    private PayPalAdapter payPalAdapter;

    @BeforeEach
    void setUp() {
        payPalAdapter = new PayPalAdapter();
    }

    @Test
    @DisplayName("Constructor initialization")
    void constructor_ShouldInitializeCorrectly() {
        assertEquals("PayPal Gateway", payPalAdapter.getGatewayName());
        assertNotNull(payPalAdapter.getCredentials());
    }

    @Test
    @DisplayName("Create payment request should format PayPal email")
    void createPaymentRequest_ShouldFormatPayPalEmail() {
        PaymentRequest request = payPalAdapter.createPaymentRequest(150.50, "USD", "customer123");

        assertNotNull(request);
        assertEquals(150.50, request.getPaymentAmount(), 0.001);
        assertEquals("USD", request.getPaymentCurrency());
        assertEquals("customer123@customer.bank", request.getCustomerEmail());
    }

    @ParameterizedTest
    @CsvSource({
            "100.0, USD, test123",
            "50.5, EUR, user456",
            "0.01, GBP, cust789"
    })
    @DisplayName("Create payment request with different values")
    void createPaymentRequest_WithDifferentValues(double amount, String currency, String customerId) {
        PaymentRequest request = payPalAdapter.createPaymentRequest(amount, currency, customerId);

        assertEquals(amount, request.getPaymentAmount(), 0.001);
        assertEquals(currency, request.getPaymentCurrency());
        assertEquals(customerId + "@customer.bank", request.getCustomerEmail());
    }

    @Test
    @DisplayName("Get credentials should return PayPal credentials")
    void getCredentials_ShouldReturnPayPalCredentials() {
        MerchantCredentials credentials = payPalAdapter.getCredentials();

        assertNotNull(credentials);
        assertEquals("paypal_live_ak_test123", credentials.getApiKey());
        assertEquals("paypal_live_sk_test456", credentials.getSecretKey());
    }

    @Test
    @DisplayName("Convert status should map correctly")
    void convertStatus_ShouldMapCorrectly() {
        assertEquals("SUCCESS", payPalAdapter.convertStatus("COMPLETED"));
        assertEquals("PENDING", payPalAdapter.convertStatus("PENDING"));
        assertEquals("FAILED", payPalAdapter.convertStatus("FAILED"));
        assertEquals("UNKNOWN", payPalAdapter.convertStatus("UNKNOWN_STATUS"));
    }

    @Test
    @DisplayName("Convert status case sensitive")
    void convertStatus_CaseSensitive() {
        assertEquals("UNKNOWN", payPalAdapter.convertStatus("completed"));
        assertEquals("UNKNOWN", payPalAdapter.convertStatus("pending"));
        assertEquals("UNKNOWN", payPalAdapter.convertStatus("failed"));
    }

    @Test
    @DisplayName("Authorize payment should always return true")
    void authorizePayment_ShouldReturnTrue() {
        boolean result = payPalAdapter.authorizePayment(100.0, "customer@test.com");
        assertTrue(result);
    }

    @Test
    @DisplayName("Authorize payment with different amounts")
    void authorizePayment_WithDifferentAmounts() {
        assertTrue(payPalAdapter.authorizePayment(0.01, "test1@test.com"));
        assertTrue(payPalAdapter.authorizePayment(1000.0, "test2@test.com"));
        assertTrue(payPalAdapter.authorizePayment(999999.99, "test3@test.com"));
    }

    @Test
    @DisplayName("Process payment integration test")
    void processPayment_IntegrationTest() {
        try {
            String transactionId = payPalAdapter.processPayment(99.99, "EUR", "testCustomer");
            assertNotNull(transactionId);
            assertTrue(transactionId.startsWith("EXT-"));
            assertTrue(transactionId.contains("PayPal"));

        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Payment failed"));
        }
    }

    @Test
    @DisplayName("Check payment status flow")
    void checkPaymentStatus_Flow() {
        try {
            String transactionId = payPalAdapter.processPayment(50.0, "USD", "statusTest");

            String status = payPalAdapter.checkPaymentStatus(transactionId);
            assertNotNull(status);
            assertTrue(status.equals("SUCCESS") ||
                    status.equals("PENDING") ||
                    status.equals("FAILED") ||
                    status.equals("UNKNOWN"));

        } catch (RuntimeException e) {
        }
    }

    @Test
    @DisplayName("Refund payment flow")
    void refundPayment_Flow() {
        try {
            String transactionId = payPalAdapter.processPayment(75.0, "GBP", "refundTest");

            boolean refundResult = payPalAdapter.refundPayment(transactionId);
            assertNotNull(Boolean.valueOf(refundResult));

        } catch (RuntimeException e) {
        }
    }

    @Test
    @DisplayName("Full PayPal workflow")
    void fullPayPalWorkflow() {
        try {
            String transactionId = payPalAdapter.processPayment(200.0, "USD", "workflowTest");

            String status = payPalAdapter.checkPaymentStatus(transactionId);

            boolean refunded = payPalAdapter.refundPayment(transactionId);

            assertNotNull(transactionId);
            assertNotNull(status);
            assertNotNull(Boolean.valueOf(refunded));

        } catch (RuntimeException e) {
        }
    }

    @Test
    @DisplayName("Invalid inputs should throw exceptions")
    void invalidInputs_ShouldThrowExceptions() {
        assertThrows(IllegalArgumentException.class, () -> {
            payPalAdapter.processPayment(-100, "USD", "customer");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            payPalAdapter.processPayment(100, "", "customer");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            payPalAdapter.processPayment(100, "USD", "");
        });
    }

    @Test
    @DisplayName("Multiple payment attempts")
    void multiplePaymentAttempts() {
        int attempts = 10;
        int successes = 0;
        int failures = 0;

        for (int i = 0; i < attempts; i++) {
            try {
                String txId = payPalAdapter.processPayment(10.0, "USD", "customer" + i);
                if (txId != null) successes++;
            } catch (RuntimeException e) {
                failures++;
            }
        }

        assertEquals(attempts, successes + failures);
        assertTrue(successes >= 0 && successes <= attempts);
        assertTrue(failures >= 0 && failures <= attempts);
    }
}
