package com.example.Advances.Banking.System.patterns.behavioral.chainofresponsibility;

public class TellerApprovalHandler extends ApprovalHandler {
    @Override
    public void handleRequest(double amount) {
        if (amount <= 1000) {
            System.out.println("Teller approved amount: " + amount);
        } else if (next != null) {
            next.handleRequest(amount);
        } else {
            System.out.println("No handler available for amount: " + amount);
        }
    }
}