package com.example.Advances.Banking.System.patterns.behavioral.observer;


public class InAppNotifier implements AccountObserver {

    private final String userId;

    public InAppNotifier(String userId) {
        this.userId = userId;
    }

    @Override
    public void update(AccountEvent event) {
        String notification = String.format(
                "💎 %s\n🕒 %s\n💰 %.2f $",
                event.getDescription(),
                event.getTimestamp(),
                event.getAmount()
        );

        System.out.println("[IN-APP] Notification for user: " + userId);
        System.out.println("        Notification: " + notification);

        saveToDatabase(event);
    }

    @Override
    public String getObserverId() {
        return "InAppNotifier-" + userId;
    }

    private void saveToDatabase(AccountEvent event) {
        System.out.println("        💾 Saving notification to database for user: " + userId);

    }
}