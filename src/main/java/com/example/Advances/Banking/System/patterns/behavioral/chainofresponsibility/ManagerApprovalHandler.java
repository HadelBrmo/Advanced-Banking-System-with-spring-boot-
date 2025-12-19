package com.example.Advances.Banking.System.patterns.behavioral.chainofresponsibility;

public class ManagerApprovalHandler extends ApprovalHandler {
    @Override
    public void handleRequest(double amount) {
        if (amount <= 5000) {
            System.out.println("Manager approved amount: " + amount);
        } else if (next != null) {
            next.handleRequest(amount);
        } else {
            System.out.println("No handler available for amount: " + amount);
        }
    }
}
