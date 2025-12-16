package com.example.Advances.Banking.System.nfr.testing.observe;

import com.example.Advances.Banking.System.patterns.behavioral.observer.*;
import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InAppNotifier Unit Tests")
class InAppNotifierTest {

    private InAppNotifier inAppNotifier;
    private AccountEvent testEvent;

    @BeforeEach
    void setUp() {
        inAppNotifier = new InAppNotifier("user-123");
        testEvent = new AccountEvent("WITHDRAWAL", "ACC123456", 500.0, "Withdrawal", "2024-01-15T10:30:00");
    }

    @Test
    @DisplayName("Should format notification with emojis")
    void update_ShouldFormatNotificationWithEmojis() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        inAppNotifier.update(testEvent);

        String output = outContent.toString();
        assertAll(
                () -> assertTrue(output.contains("[IN-APP] Notification for user: user-123")),
                () -> assertTrue(output.contains("💎 Withdrawal")),
                () -> assertTrue(output.contains("💰 500.00 $")),
                () -> assertTrue(output.contains("💾 Saving notification to database"))
        );

        System.setOut(System.out);
    }

    @Test
    @DisplayName("Should return correct observer ID")
    void getObserverId_ShouldReturnUserIdBasedId() {
        assertEquals("InAppNotifier-user-123", inAppNotifier.getObserverId());
    }

    @Test
    @DisplayName("Should handle different user IDs")
    void constructor_ShouldAcceptDifferentUserIds() {
        String[] userIds = {"user-123", "customer-456", "admin-789"};
        for (String userId : userIds) {
            InAppNotifier notifier = new InAppNotifier(userId);
            assertEquals("InAppNotifier-" + userId, notifier.getObserverId());
        }
    }
}