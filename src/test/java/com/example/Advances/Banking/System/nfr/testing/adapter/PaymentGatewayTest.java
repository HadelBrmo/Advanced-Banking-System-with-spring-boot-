package com.example.Advances.Banking.System.nfr.testing.adapter;

import com.example.Advances.Banking.System.patterns.structural.adapter.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PaymentGateway Interface Tests")
class PaymentGatewayTest {

    @Test
    @DisplayName("Interface should have all required methods")
    void paymentGatewayInterface_ShouldHaveRequiredMethods() {
        PaymentGateway gateway = new PaymentGateway() {
            @Override
            public String processPayment(double amount, String currency, String customerId) {
                return "test-tx-123";
            }

            @Override
            public String checkPaymentStatus(String transactionId) {
                return "SUCCESS";
            }

            @Override
            public boolean refundPayment(String transactionId) {
                return true;
            }

            @Override
            public String getGatewayName() {
                return "Test Gateway";
            }
        };

        assertNotNull(gateway);
        assertEquals("Test Gateway", gateway.getGatewayName());
    }

    @Test
    @DisplayName("Get gateway name should not return null")
    void getGatewayName_ShouldReturnNonNull() {
        PaymentGateway gateway = new PayPalAdapter();
        assertNotNull(gateway.getGatewayName());
        assertFalse(gateway.getGatewayName().trim().isEmpty());
    }
}