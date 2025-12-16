package com.example.Advances.Banking.System.patterns.behavioral.observer;

import java.time.LocalDateTime;

public class EmailNotifier implements AccountObserver {

    private final String emailAddress;
    private final String customerName;

    public EmailNotifier(String emailAddress, String customerName) {
        this.emailAddress = emailAddress;
        this.customerName = customerName;
    }

    @Override
    public void update(AccountEvent event) {
        if (event == null) {
            logNullEvent();
            return;
        }

        String subject = formatSubject(event);
        String body = formatBody(event);

        sendEmail(subject, body);
    }

    private void logNullEvent() {
        System.out.println("[EMAIL WARNING] Received null event for: " + emailAddress);
        System.out.println("       Skipping email notification...");
    }

    String formatSubject(AccountEvent event) {
        if (event == null) {
            return "Bank Notification - UNKNOWN_EVENT";
        }
        return "Bank Notification - " + event.getEventType();
    }

    String formatBody(AccountEvent event) {
        if (event == null) {
            return String.format("""
                Dear %s,
                
                A transaction event was received but could not be processed.
                
                Please contact your bank for more information.
                
                Best regards,
                Advanced Banking Team
                """, customerName);
        }

        return String.format("""
            Dear %s,
            
            %s
            
            Transaction Details:
            - Account: %s
            - Amount: %.2f $
            - Time: %s
            
            Best regards,
            Advanced Banking Team
            """,
                customerName,
                event.getDescription(),
                event.getAccountNumber(),
                event.getAmount(),
                event.getTimestamp()
        );
    }

    private void sendEmail(String subject, String body) {
        System.out.println("[EMAIL] Sending to: " + emailAddress);
        System.out.println("       Subject: " + subject);
        System.out.println("       Body preview: " +
                body.substring(0, Math.min(50, body.length())) + "...");
    }

    @Override
    public String getObserverId() {
        return "EmailNotifier-" + emailAddress;
    }
}