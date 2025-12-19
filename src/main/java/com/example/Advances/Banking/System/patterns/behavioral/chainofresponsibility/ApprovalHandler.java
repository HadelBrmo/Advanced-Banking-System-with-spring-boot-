package com.example.Advances.Banking.System.patterns.behavioral.chainofresponsibility;

public abstract class ApprovalHandler {
    protected ApprovalHandler next;

    public void setNext(ApprovalHandler next) {
        this.next = next;
    }

    public abstract void handleRequest(double amount);
}