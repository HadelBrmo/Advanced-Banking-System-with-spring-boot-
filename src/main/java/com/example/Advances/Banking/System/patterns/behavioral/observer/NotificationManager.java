package com.example.Advances.Banking.System.patterns.behavioral.observer;


import java.util.HashMap;
import java.util.Map;


public class NotificationManager {

    private final Map<String, AccountSubject> accountSubjects = new HashMap<>();

    public AccountSubject registerAccount(String accountNumber) {
        AccountSubject subject = new AccountSubject(accountNumber);
        accountSubjects.put(accountNumber, subject);
        System.out.println("[INFO] Account registered for notifications: " + accountNumber);
        return subject;
    }


    public void addObserverToAccount(String accountNumber, AccountObserver observer) {
        AccountSubject subject = accountSubjects.get(accountNumber);
        if (subject != null) {
            subject.attach(observer);
        } else {
            System.out.println("[ERROR] Account not registered: " + accountNumber);
        }
    }

    public void sendEvent(String accountNumber, String eventType,
                          double amount, String description) {
        AccountSubject subject = accountSubjects.get(accountNumber);
        if (subject != null) {
            subject.triggerEvent(eventType, amount, description);
        } else {
            System.out.println("[ERROR] Account not registered: " + accountNumber);
        }
    }


    public int getObserverCount(String accountNumber) {
        AccountSubject subject = accountSubjects.get(accountNumber);
        return subject != null ? subject.getObserverCount() : 0;
    }


    public void setupDefaultNotifications(String accountNumber, String email,
                                          String phone, String userId, String customerName) {
        AccountSubject subject = registerAccount(accountNumber);

        subject.attach(new EmailNotifier(email, customerName));
        subject.attach(new SMSNotifier(phone));
        subject.attach(new InAppNotifier(userId));

        System.out.println("[INFO] Default notifications setup for account: " + accountNumber);
    }
}
