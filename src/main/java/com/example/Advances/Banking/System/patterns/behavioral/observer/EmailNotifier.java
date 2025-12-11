package com.example.Advances.Banking.System.patterns.behavioral.observer;

public class EmailNotifier implements AccountObserver {

    private final String emailAddress;
    private final String customerName;

    public EmailNotifier(String emailAddress, String customerName) {
        this.emailAddress = emailAddress;
        this.customerName = customerName;
    }

    @Override
    public void update(AccountEvent event) {
        String subject = "Bank Notification - " + event.getEventType();
        String body = String.format("""
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

        System.out.println("[EMAIL] Sending to: " + emailAddress);
        System.out.println("       Subject: " + subject);
        System.out.println("       Body preview: " + body.substring(0, Math.min(50, body.length())) + "...");
    }

    @Override
    public String getObserverId() {
        return "EmailNotifier-" + emailAddress;
    }
}
