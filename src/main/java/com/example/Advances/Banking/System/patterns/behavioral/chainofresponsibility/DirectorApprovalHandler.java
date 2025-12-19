package com.example.Advances.Banking.System.patterns.behavioral.chainofresponsibility;

public class DirectorApprovalHandler extends ApprovalHandler {
    @Override
    public void handleRequest(double amount) {
        if (amount <= 20000) {
            System.out.println("Director approved amount: " + amount);
        } else if (next != null) {
            next.handleRequest(amount);
        } else {
            System.out.println("No handler available for amount: " + amount);
        }
    }
}
