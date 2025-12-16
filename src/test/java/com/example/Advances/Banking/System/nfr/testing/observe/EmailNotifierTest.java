package com.example.Advances.Banking.System.nfr.testing.observe;

import com.example.Advances.Banking.System.patterns.behavioral.observer.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EmailNotifier Unit Tests")
class EmailNotifierTest {

    private EmailNotifier emailNotifier;
    private AccountEvent testEvent;

    @BeforeEach
    void setUp() {
        emailNotifier = new EmailNotifier("customer@test.com", "John Doe");
        testEvent = new AccountEvent("WITHDRAWAL", "ACC123456", 500.0, "Withdrawal processed", "2024-01-15T10:30:00");
    }


    @Test
    @DisplayName("Should return correct observer ID")
    void getObserverId_ShouldReturnEmailBasedId() {
        assertEquals("EmailNotifier-customer@test.com", emailNotifier.getObserverId());
    }

    @Test
    @DisplayName("Should handle null event gracefully")
    void update_WithNullEvent_ShouldNotCrash() {
        // Act & Assert
        assertDoesNotThrow(() -> emailNotifier.update(null));
    }

    @ParameterizedTest
    @CsvSource({
            "test@example.com, Test User",
            "user.name@domain.co, User Name",
            "a@b.c, Short Name"
    })
    @DisplayName("Constructor with various parameters")
    void constructor_ShouldAcceptValidParameters(String email, String name) {
        EmailNotifier notifier = new EmailNotifier(email, name);
        assertNotNull(notifier.getObserverId());
        assertTrue(notifier.getObserverId().contains(email));
    }
}