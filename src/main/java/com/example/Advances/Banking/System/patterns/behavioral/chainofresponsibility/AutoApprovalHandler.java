package com.example.Advances.Banking.System.patterns.behavioral.chainofresponsibility;

public class AutoApprovalHandler extends ApprovalHandler {
    @Override
    public void handleRequest(double amount) {
        System.out.println("Auto-approved amount: " + amount);
    }
}