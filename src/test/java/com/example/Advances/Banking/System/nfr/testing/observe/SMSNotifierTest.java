package com.example.Advances.Banking.System.nfr.testing.observe;

import com.example.Advances.Banking.System.patterns.behavioral.observer.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SMSNotifier Unit Tests")
class SMSNotifierTest {

    private SMSNotifier smsNotifier;
    private AccountEvent testEvent;

    @BeforeEach
    void setUp() {
        smsNotifier = new SMSNotifier("+963123456789");
        testEvent = new AccountEvent("WITHDRAWAL", "ACC1234567890", 500.0, "Withdrawal", "2024-01-15T10:30:00");
    }

    @Test
    @DisplayName("Should mask sensitive information")
    void update_ShouldMaskPhoneAndAccountNumbers() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        smsNotifier.update(testEvent);

        String output = outContent.toString();
        assertAll(
                () -> assertTrue(output.contains("[SMS] Sending to: ***6789")),
                () -> assertTrue(output.contains("Account: ****7890")),
                () -> assertTrue(output.contains("[Bank] Withdrawal")),
                () -> assertTrue(output.contains("Amount: 500.00"))
        );

        System.setOut(System.out);
    }

    @Test
    @DisplayName("Should return correct observer ID")
    void getObserverId_ShouldReturnPhoneBasedId() {
        assertEquals("SMSNotifier-+963123456789", smsNotifier.getObserverId());
    }

    @ParameterizedTest
    @CsvSource({
            "+963987654321, ***4321",
            "1234567890, ***7890",
            "1234, 1234"
    })
    @DisplayName("Phone number masking")
    void maskPhoneNumber_ShouldMaskCorrectly(String phone, String expectedMask) {
        SMSNotifier notifier = new SMSNotifier(phone);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        notifier.update(testEvent);

        String output = outContent.toString();
        assertTrue(output.contains(expectedMask));
        System.setOut(System.out);
    }
}
