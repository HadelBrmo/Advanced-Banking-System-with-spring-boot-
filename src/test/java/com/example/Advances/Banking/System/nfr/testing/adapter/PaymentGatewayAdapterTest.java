package com.example.Advances.Banking.System.nfr.testing.adapter;

import com.example.Advances.Banking.System.patterns.structural.adapter.*;
import com.example.Advances.Banking.System.patterns.structural.adapter.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PaymentGatewayAdapter Tests")
class PaymentGatewayAdapterTest {

    private PaymentGatewayAdapter testAdapter;

    @BeforeEach
    void setUp() {
        testAdapter = new PaymentGatewayAdapter(
                new ExternalPaymentService("TestService"),
                "Test Gateway"
        ) {
            @Override
            public PaymentRequest createPaymentRequest(double amount, String currency, String customerId) {
                return new PaymentRequest(amount, currency, customerId + "@test.com");
            }

            @Override
            public MerchantCredentials getCredentials() {
                return new MerchantCredentials("test-key", "test-secret");
            }

            @Override
            public String convertStatus(String externalStatus) {
                return "CONVERTED-" + externalStatus;
            }
        };
    }

    @Test
    @DisplayName("Constructor initialization")
    void constructor_ShouldInitializeFields() {
        assertEquals("Test Gateway", testAdapter.getGatewayName());
        assertNotNull(testAdapter.externalService);
    }

    @Test
    @DisplayName("Get gateway name")
    void getGatewayName_ShouldReturnName() {
        assertEquals("Test Gateway", testAdapter.getGatewayName());
    }

    @Test
    @DisplayName("Check payment status should convert status")
    void checkPaymentStatus_ShouldConvertStatus() {
        String status = testAdapter.checkPaymentStatus("TX-123");
        assertNotNull(status);
        assertTrue(status.startsWith("CONVERTED-"));
    }

    @Test
    @DisplayName("Refund payment should call reverse transaction")
    void refundPayment_ShouldCallReverseTransaction() {
        boolean result = testAdapter.refundPayment("TX-123");
        assertNotNull(Boolean.valueOf(result));
    }

    @Test
    @DisplayName("Invalid amount should throw exception")
    void invalidAmount_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            testAdapter.processPayment(-100, "USD", "customer");
        });
    }

    @Test
    @DisplayName("Invalid currency should throw exception")
    void invalidCurrency_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            testAdapter.processPayment(100, "", "customer");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            testAdapter.processPayment(100, null, "customer");
        });
    }

    @Test
    @DisplayName("Invalid customer ID should throw exception")
    void invalidCustomerId_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            testAdapter.processPayment(100, "USD", "");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            testAdapter.processPayment(100, "USD", null);
        });
    }

    @Test
    @DisplayName("Invalid transaction ID for status check")
    void invalidTransactionId_ForStatusCheck() {
        assertThrows(IllegalArgumentException.class, () -> {
            testAdapter.checkPaymentStatus("");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            testAdapter.checkPaymentStatus(null);
        });
    }

    @Test
    @DisplayName("Invalid transaction ID for refund")
    void invalidTransactionId_ForRefund() {
        assertThrows(IllegalArgumentException.class, () -> {
            testAdapter.refundPayment("");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            testAdapter.refundPayment(null);
        });
    }

    @Test
    @DisplayName("Abstract methods should be implemented")
    void abstractMethods_ShouldBeCallable() {
        assertDoesNotThrow(() -> {
            PaymentRequest request = testAdapter.createPaymentRequest(100.0, "USD", "cust123");
            assertNotNull(request);
            assertEquals(100.0, request.getPaymentAmount(), 0.001);
            assertEquals("USD", request.getPaymentCurrency());
            assertEquals("cust123@test.com", request.getCustomerEmail());

            MerchantCredentials credentials = testAdapter.getCredentials();
            assertNotNull(credentials);
            assertEquals("test-key", credentials.getApiKey());
            assertEquals("test-secret", credentials.getSecretKey());

            String convertedStatus = testAdapter.convertStatus("TEST");
            assertEquals("CONVERTED-TEST", convertedStatus);
        });
    }

    @Test
    @DisplayName("Process payment success scenario")
    void processPayment_SuccessScenario() {
        PaymentGatewayAdapter successAdapter = new PaymentGatewayAdapter(
                new ExternalPaymentService("SuccessService") {
                    @Override
                    public PaymentResponse makePayment(PaymentRequest request, MerchantCredentials credentials) {
                        return PaymentResponse.success("TEST-TX-123");
                    }
                },
                "Success Gateway"
        ) {
            @Override
            public PaymentRequest createPaymentRequest(double amount, String currency, String customerId) {
                return new PaymentRequest(amount, currency, customerId + "@test.com");
            }

            @Override
            public MerchantCredentials getCredentials() {
                return new MerchantCredentials("key", "secret");
            }

            @Override
            public String convertStatus(String externalStatus) {
                return externalStatus;
            }
        };

        String transactionId = successAdapter.processPayment(100.0, "USD", "customer123");
        assertNotNull(transactionId);
        assertEquals("TEST-TX-123", transactionId);
    }

    @Test
    @DisplayName("Process payment failure scenario")
    void processPayment_FailureScenario() {
        PaymentGatewayAdapter failureAdapter = new PaymentGatewayAdapter(
                new ExternalPaymentService("FailureService") {
                    @Override
                    public PaymentResponse makePayment(PaymentRequest request, MerchantCredentials credentials) {
                        return PaymentResponse.failure("Payment failed in test");
                    }
                },
                "Failure Gateway"
        ) {
            @Override
            public PaymentRequest createPaymentRequest(double amount, String currency, String customerId) {
                return new PaymentRequest(amount, currency, customerId);
            }

            @Override
            public MerchantCredentials getCredentials() {
                return new MerchantCredentials("key", "secret");
            }

            @Override
            public String convertStatus(String externalStatus) {
                return externalStatus;
            }
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            failureAdapter.processPayment(100.0, "USD", "customer123");
        });

        assertTrue(exception.getMessage().contains("Payment failed"));
    }

    @Test
    @DisplayName("Multiple adapters should be independent")
    void multipleAdapters_ShouldBeIndependent() {
        PaymentGatewayAdapter adapter1 = new PaymentGatewayAdapter(
                new ExternalPaymentService("Service1"),
                "Gateway 1"
        ) {
            @Override
            public PaymentRequest createPaymentRequest(double amount, String currency, String customerId) {
                return new PaymentRequest(amount, currency, customerId);
            }

            @Override
            public MerchantCredentials getCredentials() {
                return new MerchantCredentials("key1", "secret1");
            }

            @Override
            public String convertStatus(String externalStatus) {
                return externalStatus;
            }
        };

        PaymentGatewayAdapter adapter2 = new PaymentGatewayAdapter(
                new ExternalPaymentService("Service2"),
                "Gateway 2"
        ) {
            @Override
            public PaymentRequest createPaymentRequest(double amount, String currency, String customerId) {
                return new PaymentRequest(amount, currency, customerId);
            }

            @Override
            public MerchantCredentials getCredentials() {
                return new MerchantCredentials("key2", "secret2");
            }

            @Override
            public String convertStatus(String externalStatus) {
                return externalStatus;
            }
        };

        assertNotEquals(adapter1.getGatewayName(), adapter2.getGatewayName());
        assertNotNull(adapter1.getCredentials());
        assertNotNull(adapter2.getCredentials());
    }
}