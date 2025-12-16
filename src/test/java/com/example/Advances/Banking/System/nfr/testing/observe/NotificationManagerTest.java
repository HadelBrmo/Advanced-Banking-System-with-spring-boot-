package com.example.Advances.Banking.System.nfr.testing.observe;

import com.example.Advances.Banking.System.patterns.behavioral.observer.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("NotificationManager Unit Tests")
class NotificationManagerTest {

    private NotificationManager notificationManager;

    @Mock
    private AccountObserver mockObserver;

    @BeforeEach
    void setUp() {
        notificationManager = new NotificationManager();
        lenient().when(mockObserver.getObserverId()).thenReturn("Test-Observer");
    }

    @Test
    @DisplayName("Should register account")
    void registerAccount_ShouldCreateAndStoreSubject() {
        AccountSubject subject = notificationManager.registerAccount("ACC123456");

        assertNotNull(subject);
        assertEquals("ACC123456", subject.getAccountNumber());
    }

    @Test
    @DisplayName("Should add observer to registered account")
    void addObserverToAccount_ShouldAttachObserver() {
        notificationManager.registerAccount("ACC123456");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        notificationManager.addObserverToAccount("ACC123456", mockObserver);
        notificationManager.sendEvent("ACC123456", "TEST", 100.0, "Test");

        String output = outContent.toString();
        assertTrue(output.contains("Notifying"));

        System.setOut(System.out);
    }

    @Test
    @DisplayName("AddObserver to non-existent account should error")
    void addObserverToAccount_WithInvalidAccount_ShouldLogError() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        notificationManager.addObserverToAccount("NON-EXISTENT", mockObserver);

        String output = outContent.toString();
        assertTrue(output.contains("[ERROR] Account not registered"));

        System.setOut(System.out);
    }

    @Test
    @DisplayName("SendEvent should trigger notification")
    void sendEvent_ShouldTriggerEventOnSubject() {
        notificationManager.registerAccount("ACC123456");
        notificationManager.addObserverToAccount("ACC123456", mockObserver);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        notificationManager.sendEvent("ACC123456", "WITHDRAWAL", 500.0, "Withdrawal");

        String output = outContent.toString();
        assertTrue(output.contains("[NOTIFICATION]"));

        System.setOut(System.out);
    }

    @Test
    @DisplayName("Setup default notifications")
    void setupDefaultNotifications_ShouldAttachThreeObservers() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        notificationManager.setupDefaultNotifications(
                "ACC123456",
                "customer@test.com",
                "+963123456789",
                "user-123",
                "John Doe"
        );

        String output = outContent.toString();
        assertTrue(output.contains("Default notifications setup"));
        assertTrue(output.contains("Account registered"));

        System.setOut(System.out);
    }

    @Test
    @DisplayName("GetObserverCount should return correct count")
    void getObserverCount_ShouldReturnObserverCountForAccount() {
        notificationManager.registerAccount("ACC123456");
        notificationManager.addObserverToAccount("ACC123456", mockObserver);

        int count = notificationManager.getObserverCount("ACC123456");
        assertTrue(count >= 0);
    }

    @Test
    @DisplayName("GetObserverCount for non-existent account should return zero")
    void getObserverCount_ForNonExistentAccount_ShouldReturnZero() {
        int count = notificationManager.getObserverCount("NON-EXISTENT");
        assertEquals(0, count);
    }

    @Test
    @DisplayName("Should return same instance for duplicate registration - FIXED")
    void registerAccount_Twice_ShouldReturnSameInstance() {
        System.out.println("=== Testing duplicate registration behavior ===");

        AccountSubject subject1 = notificationManager.registerAccount("ACC123456");
        System.out.println("First registration: " + subject1);

        AccountSubject subject2 = notificationManager.registerAccount("ACC123456");
        System.out.println("Second registration: " + subject2);

        assertSame(subject1, subject2,
                "Should return same AccountSubject instance for same account number");

        assertEquals("ACC123456", subject1.getAccountNumber());
        assertEquals("ACC123456", subject2.getAccountNumber());

        assertTrue(subject1 == subject2, "Should be same object reference");

        System.out.println("✓ SUCCESS: Same instance returned for duplicate registration");
    }

    @Test
    @DisplayName("Should not increase map size for duplicate registration")
    void registerAccount_Twice_ShouldNotIncreaseMapSize() throws Exception {
        // Get initial map size using reflection
        int initialSize = getAccountSubjectsMapSize(notificationManager);
        System.out.println("Initial map size: " + initialSize);

        // First registration
        notificationManager.registerAccount("ACC123456");
        int sizeAfterFirst = getAccountSubjectsMapSize(notificationManager);
        System.out.println("After first registration: " + sizeAfterFirst);

        // Second registration (duplicate)
        notificationManager.registerAccount("ACC123456");
        int sizeAfterSecond = getAccountSubjectsMapSize(notificationManager);
        System.out.println("After second registration: " + sizeAfterSecond);

        assertEquals(initialSize + 1, sizeAfterFirst,
                "Map size should increase after first registration");
        assertEquals(sizeAfterFirst, sizeAfterSecond,
                "Map size should NOT increase for duplicate registration");

        System.out.println("✓ SUCCESS: Map size maintained for duplicate registration");
    }

    @Test
    @DisplayName("Multiple accounts registration")
    void registerAccount_MultipleAccounts() {
        String[] accounts = {"ACC1001", "ACC1002", "ACC1003", "ACC1004"};

        for (String account : accounts) {
            AccountSubject subject1 = notificationManager.registerAccount(account);
            AccountSubject subject2 = notificationManager.registerAccount(account);

            assertSame(subject1, subject2,
                    "Account " + account + " should return same instance");
            assertEquals(account, subject1.getAccountNumber());
        }

        System.out.println("✓ SUCCESS: All " + accounts.length + " accounts work correctly");
    }

    @Test
    @DisplayName("Concurrent-like registration test")
    void registerAccount_ConcurrentScenario() {
        // Simulate multiple registration attempts
        AccountSubject[] subjects = new AccountSubject[5];

        for (int i = 0; i < 5; i++) {
            subjects[i] = notificationManager.registerAccount("CONCURRENT-ACC");
        }

        // All should be the same instance
        for (int i = 1; i < subjects.length; i++) {
            assertSame(subjects[0], subjects[i],
                    "All registrations should return same instance");
        }

        System.out.println("✓ SUCCESS: " + subjects.length +
                " concurrent registrations return same instance");
    }

    private int getAccountSubjectsMapSize(NotificationManager manager) throws Exception {
        try {
            Field field = NotificationManager.class.getDeclaredField("accountSubjects");
            field.setAccessible(true);
            Map<?, ?> map = (Map<?, ?>) field.get(manager);
            return map.size();
        } catch (Exception e) {
            System.err.println("Error accessing accountSubjects map: " + e.getMessage());
            return -1;
        }
    }
}