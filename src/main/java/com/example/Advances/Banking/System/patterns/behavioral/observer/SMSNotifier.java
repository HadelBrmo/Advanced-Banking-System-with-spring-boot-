package com.example.Advances.Banking.System.patterns.behavioral.observer;


public class SMSNotifier implements AccountObserver {

    private final String phoneNumber;

    public SMSNotifier(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void update(AccountEvent event) {
        String message = String.format(
                "[Bank] %s. Account: %s, Amount: %.2f",
                event.getDescription(),
                maskAccountNumber(event.getAccountNumber()),
                event.getAmount()
        );

        System.out.println("[SMS] Sending to: " + maskPhoneNumber(phoneNumber));
        System.out.println("     Message: " + message);
    }

    @Override
    public String getObserverId() {
        return "SMSNotifier-" + phoneNumber;
    }

    private String maskPhoneNumber(String phone) {
        if (phone.length() > 4) {
            return "***" + phone.substring(phone.length() - 4);
        }
        return phone;
    }

    private String maskAccountNumber(String account) {
        if (account.length() > 4) {
            return "****" + account.substring(account.length() - 4);
        }
        return account;
    }
}
