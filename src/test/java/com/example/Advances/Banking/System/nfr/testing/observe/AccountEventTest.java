package com.example.Advances.Banking.System.nfr.testing.observe;

import com.example.Advances.Banking.System.patterns.behavioral.observer.AccountEvent;
import org.junit.jupiter.api.*;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AccountEvent Unit Tests")
class AccountEventTest {

    @Test
    @DisplayName("Should create event with correct values")
    void constructor_ShouldSetAllFields() {
        // Given
        String eventType = "DEPOSIT";
        String accountNumber = "ACC123456";
        double amount = 1000.0;
        String description = "Salary deposit";

        // When
        AccountEvent event = new AccountEvent(eventType, accountNumber, amount, description, "2024-01-15T10:30:00");

        // Then
        assertAll(
                () -> assertEquals(eventType, event.getEventType()),
                () -> assertEquals(accountNumber, event.getAccountNumber()),
                () -> assertEquals(amount, event.getAmount(), 0.001),
                () -> assertEquals(description, event.getDescription()),
                () -> assertNotNull(event.getTimestamp()),
                () -> assertTrue(event.getTimestamp() instanceof Date)
        );
    }

    @Test
    @DisplayName("Should set timestamp automatically")
    void constructor_ShouldSetCurrentTimestamp() {
        Date before = new Date();
        AccountEvent event = new AccountEvent("TEST", "ACC123", 100.0, "Test", "2024-01-15T10:30:00");
        Date after = new Date();

        Date timestamp = event.getTimestamp();
        //timestamp ≥ before
        assertTrue(timestamp.after(before) || timestamp.equals(before));
        //timestamp < before
        assertTrue(timestamp.before(after) || timestamp.equals(after));
    }

    @Test
    @DisplayName("ToString should format correctly")
    void toString_ShouldFormatEventDetails() {
        AccountEvent event = new AccountEvent("WITHDRAWAL", "ACC123456", 500.0,
                "ATM withdrawal", "2024-01-15T10:30:00");

        String result = event.toString();
        System.out.println("Actual toString(): " + result);


        assertAll(
                () -> assertTrue(result.contains("ACC123456"), "Should contain account number"),
                () -> assertTrue(result.contains("500.00"), "Should contain formatted amount"),
                () -> assertTrue(result.contains("ATM withdrawal"), "Should contain description"),
                () -> assertTrue(result.contains("Account:"), "Should contain 'Account:' label"),
                () -> assertTrue(result.contains("Amount:"), "Should contain 'Amount:' label")
        );
    }

    @Test
    @DisplayName("Should handle zero amount")
    void constructor_WithZeroAmount_ShouldWork() {
        AccountEvent event = new AccountEvent("TEST", "ACC123", 0.0, "Zero amount", "2024-01-15T10:30:00");
        assertEquals(0.0, event.getAmount(), 0.001);
    }

    @Test
    @DisplayName("Should handle negative amount")
    void constructor_WithNegativeAmount_ShouldWork() {
        AccountEvent event = new AccountEvent("OVERDRAFT", "ACC123", -100.0, "Overdraft fee", "2024-01-15T10:30:00");
        assertEquals(-100.0, event.getAmount(), 0.001);
    }

    @Test
    @DisplayName("Should handle empty description")
    void constructor_WithEmptyDescription_ShouldWork() {
        AccountEvent event = new AccountEvent("TEST", "ACC123", 100.0, "", "2024-01-15T10:30:00");
        assertEquals("", event.getDescription());
    }
}
