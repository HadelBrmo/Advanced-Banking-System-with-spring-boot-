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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AccountSubject Unit Tests")
@MockitoSettings(strictness = Strictness.LENIENT)
class AccountSubjectTest {

    private AccountSubject accountSubject;

    @Mock
    private AccountObserver mockObserver1;

    @Mock
    private AccountObserver mockObserver2;

    @BeforeEach
    void setUp() {
        accountSubject = new AccountSubject("ACC123456");
        when(mockObserver1.getObserverId()).thenReturn("Observer-1");
        when(mockObserver2.getObserverId()).thenReturn("Observer-2");
    }

    @Test
    @DisplayName("Should attach observers")
    void attach_ShouldAddObserverToList() {
        accountSubject.attach(mockObserver1);
        accountSubject.attach(mockObserver2);

        assertEquals(2, accountSubject.getObserverCount());
        assertTrue(accountSubject.getObservers().contains(mockObserver1));
        assertTrue(accountSubject.getObservers().contains(mockObserver2));
    }

    @Test
    @DisplayName("Should not attach duplicate observers")
    void attach_ShouldNotAddDuplicateObserver() {
        accountSubject.attach(mockObserver1);
        accountSubject.attach(mockObserver1);

        assertEquals(1, accountSubject.getObserverCount());
    }

    @Test
    @DisplayName("Should detach observers")
    void detach_ShouldRemoveObserver() {
        accountSubject.attach(mockObserver1);
        accountSubject.attach(mockObserver2);
        accountSubject.detach(mockObserver1);

        assertEquals(1, accountSubject.getObserverCount());
        assertFalse(accountSubject.getObservers().contains(mockObserver1));
        assertTrue(accountSubject.getObservers().contains(mockObserver2));
    }

    @Test
    @DisplayName("Should notify all observers")
    void notifyObservers_ShouldCallUpdateOnAllObservers() {
        AccountEvent event = new AccountEvent("TEST", "ACC123456", 100.0, "Test", "2024-01-15T10:30:00");
        accountSubject.attach(mockObserver1);
        accountSubject.attach(mockObserver2);

        accountSubject.notifyObservers(event);

        verify(mockObserver1, times(1)).update(event);
        verify(mockObserver2, times(1)).update(event);
    }

    @Test
    @DisplayName("TriggerEvent should create event and notify")
    void triggerEvent_ShouldCreateEventAndNotify() {
        accountSubject.attach(mockObserver1);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        accountSubject.triggerEvent("DEPOSIT", 1000.0, "Deposit");

        verify(mockObserver1, times(1)).update(any(AccountEvent.class));
        String output = outContent.toString();
        assertTrue(output.contains("[NOTIFICATION]"));


        System.setOut(System.out);
    }

    @Test
    @DisplayName("Should return correct account number")
    void getAccountNumber_ShouldReturnConstructorValue() {
        AccountSubject subject = new AccountSubject("SPECIAL-ACC");
        assertEquals("SPECIAL-ACC", subject.getAccountNumber());
    }

    @Test
    @DisplayName("Should handle empty observer list")
    void notifyObservers_WithNoObservers_ShouldNotCrash() {
        AccountEvent event = new AccountEvent("TEST", "ACC123", 100.0, "Test", "2024-01-15T10:30:00");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        assertDoesNotThrow(() -> accountSubject.notifyObservers(event));

        String output = outContent.toString();
        assertTrue(output.contains("Notifying 0 observers"));

        System.setOut(System.out);
    }
}