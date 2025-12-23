package com.example.Advances.Banking.System.nfr.testing.observe;

import com.example.Advances.Banking.System.patterns.behavioral.observer.*;
import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Observer Pattern Integration Test")
class ObserverPatternIntegrationTest {

    private NotificationManager notificationManager;

    @BeforeEach
    void setUp() {
        notificationManager = new NotificationManager();
    }

    @Test
    @DisplayName("Full notification flow with real observers")
    void integration_FullNotificationFlow() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        EmailNotifier emailNotifier = new EmailNotifier("test@example.com", "Test User");
        SMSNotifier smsNotifier = new SMSNotifier("+963987654321");
        InAppNotifier inAppNotifier = new InAppNotifier("test-user-123");

        AccountSubject subject = notificationManager.registerAccount("ACC-INTEGRATION");
        subject.attach(emailNotifier);
        subject.attach(smsNotifier);
        subject.attach(inAppNotifier);

        subject.triggerEvent("INTEGRATION_TEST", 1234.56, "Integration test");

        String output = outContent.toString();
        assertAll(
                () -> assertTrue(output.contains("[EMAIL]"), "Email should be triggered"),
                () -> assertTrue(output.contains("[SMS]"), "SMS should be triggered"),
                () -> assertTrue(output.contains("[IN-APP]"), "In-app should be triggered"),
                () -> assertTrue(output.contains("INTEGRATION_TEST"), "Event type should appear"),
                () -> assertTrue(output.contains("1234.56"), "Amount should appear"),
                () -> assertTrue(output.contains("ACC-INTEGRATION"), "Account should appear")
        );

        System.setOut(System.out);
    }

    @Test
    @DisplayName("Multiple accounts with different observers")
    void integration_MultipleAccountsDifferentObservers() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        AccountSubject acc1 = notificationManager.registerAccount("ACC-001");
        acc1.attach(new EmailNotifier("acc1@test.com", "Account 1"));

        AccountSubject acc2 = notificationManager.registerAccount("ACC-002");
        acc2.attach(new SMSNotifier("+963111111111"));

        acc1.triggerEvent("DEPOSIT", 100.0, "Deposit to acc1");
        acc2.triggerEvent("WITHDRAWAL", 50.0, "Withdrawal from acc2");

        String output = outContent.toString();
        int acc1Notifications = countSubstring(output, "ACC-001");
        int acc2Notifications = countSubstring(output, "ACC-002");

        assertTrue(acc1Notifications > 0, "ACC-001 should have notifications");
        assertTrue(acc2Notifications > 0, "ACC-002 should have notifications");

        System.setOut(System.out);
    }

    @Test
    @DisplayName("Dynamic observer management")
    void integration_DynamicObserverManagement() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        AccountSubject subject = new AccountSubject("ACC-DYNAMIC");
        EmailNotifier emailNotifier = new EmailNotifier("dynamic@test.com", "Dynamic");

        // Attach and trigger
        subject.attach(emailNotifier);
        subject.triggerEvent("FIRST", 100.0, "With observer");

        String firstOutput = outContent.toString();
        int firstCount = countSubstring(firstOutput, "\\[NOTIFICATION\\]");

        // Detach and trigger again
        outContent.reset();
        subject.detach(emailNotifier);
        subject.triggerEvent("SECOND", 200.0, "Without observer");

        String secondOutput = outContent.toString();
        int secondCount = countSubstring(secondOutput, "\\[NOTIFICATION\\]");

        assertAll(
                () -> assertEquals(1, firstCount, "Should notify with observer"),
                () -> assertEquals(1, secondCount, "Should still attempt notification"),
                () -> assertTrue(secondOutput.contains("Notifying 0 observers"))
        );

        System.setOut(System.out);
    }


    private int countSubstring(String text, String substring) {

        return text.split(substring).length - 1;
    }
}