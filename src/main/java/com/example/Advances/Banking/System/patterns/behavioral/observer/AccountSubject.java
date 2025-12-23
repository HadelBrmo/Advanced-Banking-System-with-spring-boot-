package com.example.Advances.Banking.System.patterns.behavioral.observer;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class AccountSubject {

    @Getter
    private final String accountNumber;
    private final List<AccountObserver> observers = new ArrayList<>();

    public AccountSubject(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    //  Add new observer
    public void attach(AccountObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("[INFO] Observer added: " + observer.getObserverId() +
                    " for account: " + accountNumber);
        }
    }


     // Remove observer
    public void detach(AccountObserver observer) {
        observers.remove(observer);
        System.out.println("[INFO] Observer removed: " + observer.getObserverId());
    }


    public void notifyObservers(AccountEvent event) {
        System.out.println("\n[NOTIFICATION] Notifying " + observers.size() + " observers:");
        for (AccountObserver observer : observers) {
            observer.update(event);
        }
    }


    public void triggerEvent(String eventType, double amount, String description) {
        AccountEvent event = new AccountEvent(eventType, accountNumber, amount, description, "2024-01-15T10:30:00");
        notifyObservers(event);
    }

    public List<AccountObserver> getObservers() { return new ArrayList<>(observers); }
    public int getObserverCount() { return observers.size(); }
}
