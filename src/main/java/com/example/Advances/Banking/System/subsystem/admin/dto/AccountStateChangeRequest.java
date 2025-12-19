package com.example.Advances.Banking.System.subsystem.admin.dto;

public class AccountStateChangeRequest {
    public String accountId;
    public String newState; // ACTIVE, FROZEN, SUSPENDED, CLOSED
}