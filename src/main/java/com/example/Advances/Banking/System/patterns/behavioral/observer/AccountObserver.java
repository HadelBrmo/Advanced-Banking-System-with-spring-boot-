package com.example.Advances.Banking.System.patterns.behavioral.observer;


public interface AccountObserver {

    void update(AccountEvent event);

    String getObserverId();
}
