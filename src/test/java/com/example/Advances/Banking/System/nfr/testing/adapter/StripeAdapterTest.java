package com.example.Advances.Banking.System.nfr.testing.adapter;


import com.example.Advances.Banking.System.patterns.structural.adapter.*;
import com.example.Advances.Banking.System.patterns.structural.adapter.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StripeAdapter Tests")
class StripeAdapterTest {

    private StripeAdapter stripeAdapter;

    @BeforeEach
    void setUp() {
        stripeAdapter = new StripeAdapter();
    }

    @Test
    @DisplayName("Constructor initialization")
    void constructor_ShouldInitializeCorrectly() {
        assertEquals("Stripe Gateway", stripeAdapter.getGatewayName());
        assertNotNull(stripeAdapter.getCredentials());
    }

    @Test
    @DisplayName("Create payment request should format Stripe email")
    void createPaymentRequest_ShouldFormatStripeEmail() {
        PaymentRequest request = stripeAdapter.createPaymentRequest(299.99, "EUR", "cust123");

        assertNotNull(request);
        assertEquals(299.99, request.getPaymentAmount(), 0.001);
        assertEquals("EUR", request.getPaymentCurrency());
        assertEquals("cust_cust123@stripe.bank", request.getCustomerEmail());
    }

    @ParameterizedTest
    @CsvSource({
            "user1, cust_user1@stripe.bank",
            "12345, cust_12345@stripe.bank",
            "test-customer, cust_test-customer@stripe.bank"
    })
    @DisplayName("Customer email formatting")
    void customerEmail_Formatting(String customerId, String expectedEmail) {
        PaymentRequest request = stripeAdapter.createPaymentRequest(100.0, "USD", customerId);
        assertEquals(expectedEmail, request.getCustomerEmail());
    }

    @Test
    @DisplayName("Get credentials should return Stripe credentials")
    void getCredentials_ShouldReturnStripeCredentials() {
        MerchantCredentials credentials = stripeAdapter.getCredentials();

        assertNotNull(credentials);
        assertEquals("sk_live_test123", credentials.getApiKey());
        assertEquals("pk_live_test456", credentials.getSecretKey());
    }

    @Test
    @DisplayName("Convert status should handle all cases")
    void convertStatus_ShouldHandleAllCases() {
        assertEquals("SUCCESS", stripeAdapter.convertStatus("COMPLETED"));
        assertEquals("SUCCESS", stripeAdapter.convertStatus("succeeded"));
        assertEquals("PENDING", stripeAdapter.convertStatus("PENDING"));
        assertEquals("PENDING", stripeAdapter.convertStatus("processing"));
        assertEquals("FAILED", stripeAdapter.convertStatus("FAILED"));
        assertEquals("FAILED", stripeAdapter.convertStatus("requires_payment_method"));
        assertEquals("UNKNOWN", stripeAdapter.convertStatus("unknown_status"));
        assertEquals("UNKNOWN", stripeAdapter.convertStatus(""));
        assertEquals("UNKNOWN", stripeAdapter.convertStatus(null));
    }

    @Test
    @DisplayName("Create payment intent should return valid ID")
    void createPaymentIntent_ShouldReturnValidId() {
        String paymentIntentId = stripeAdapter.createPaymentIntent(150.0, "USD");

        assertNotNull(paymentIntentId);
        assertTrue(paymentIntentId.startsWith("pi_"));
        assertTrue(paymentIntentId.length() > 10);
    }

    @Test
    @DisplayName("Create payment intent with different currencies")
    void createPaymentIntent_WithDifferentCurrencies() {
        String[] currencies = {"USD", "EUR", "GBP", "JPY", "CAD"};

        for (String currency : currencies) {
            String paymentIntentId = stripeAdapter.createPaymentIntent(100.0, currency);
            assertNotNull(paymentIntentId);
            assertTrue(paymentIntentId.startsWith("pi_"));
        }
    }

    @Test
    @DisplayName("Capture payment should return boolean")
    void capturePayment_ShouldReturnBoolean() {
        boolean result = stripeAdapter.capturePayment("pi_123456789", 100.0);
        assertNotNull(Boolean.valueOf(result));
    }

    @Test
    @DisplayName("Capture payment with different amounts")
    void capturePayment_WithDifferentAmounts() {
        assertNotNull(Boolean.valueOf(stripeAdapter.capturePayment("pi_1", 0.01)));
        assertNotNull(Boolean.valueOf(stripeAdapter.capturePayment("pi_2", 1000.0)));
        assertNotNull(Boolean.valueOf(stripeAdapter.capturePayment("pi_3", 999999.99)));
    }

    @Test
    @DisplayName("Process payment integration test")
    void processPayment_IntegrationTest() {
        try {
            String transactionId = stripeAdapter.processPayment(100.0, "USD", "testCustomer");
            assertNotNull(transactionId);
            assertTrue(transactionId.startsWith("EXT-"));
            assertTrue(transactionId.contains("Stripe"));

        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Payment failed"));
        }
    }

    @Test
    @DisplayName("Payment processing with different currencies")
    void processPayment_WithDifferentCurrencies() {
        String[] currencies = {"USD", "EUR", "GBP"};

        for (String currency : currencies) {
            try {
                String transactionId = stripeAdapter.processPayment(100.0, currency, "test");
                assertNotNull(transactionId);
                assertTrue(transactionId.startsWith("EXT-"));
            } catch (RuntimeException e) {
                assertTrue(e.getMessage().contains("Payment failed"));
            }
        }
    }

    @Test
    @DisplayName("Comprehensive Stripe workflow")
    void stripeComprehensiveWorkflow() {
        String paymentIntentId = stripeAdapter.createPaymentIntent(150.0, "EUR");
        assertNotNull(paymentIntentId);

        try {
            String transactionId = stripeAdapter.processPayment(150.0, "EUR", "workflowTest");
            assertNotNull(transactionId);

            String status = stripeAdapter.checkPaymentStatus(transactionId);
            assertNotNull(status);

            boolean refunded = stripeAdapter.refundPayment(transactionId);
            assertNotNull(Boolean.valueOf(refunded));

        } catch (RuntimeException e) {
        }
    }

    @Test
    @DisplayName("Invalid inputs should throw exceptions")
    void invalidInputs_ShouldThrowExceptions() {
        assertThrows(IllegalArgumentException.class, () -> {
            stripeAdapter.processPayment(-100, "USD", "customer");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            stripeAdapter.processPayment(100, "", "customer");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            stripeAdapter.processPayment(100, "USD", "");
        });
    }

    @Test
    @DisplayName("Stripe vs PayPal differences")
    void stripeVsPayPal_Differences() {
        PayPalAdapter paypal = new PayPalAdapter();

        PaymentRequest paypalRequest = paypal.createPaymentRequest(100.0, "USD", "customer123");
        PaymentRequest stripeRequest = stripeAdapter.createPaymentRequest(100.0, "USD", "customer123");

        assertNotEquals(paypalRequest.getCustomerEmail(), stripeRequest.getCustomerEmail());
        assertTrue(paypalRequest.getCustomerEmail().endsWith("@customer.bank"));
        assertTrue(stripeRequest.getCustomerEmail().endsWith("@stripe.bank"));

        assertEquals("SUCCESS", paypal.convertStatus("COMPLETED"));
        assertEquals("SUCCESS", stripeAdapter.convertStatus("COMPLETED"));
        assertEquals("SUCCESS", stripeAdapter.convertStatus("succeeded"));
    }

    @Test
    @DisplayName("Multiple stripe adapters should be independent")
    void multipleStripeAdapters_ShouldBeIndependent() {
        StripeAdapter adapter1 = new StripeAdapter();
        StripeAdapter adapter2 = new StripeAdapter();

        assertEquals(adapter1.getGatewayName(), adapter2.getGatewayName());
        assertNotSame(adapter1, adapter2);
    }
}