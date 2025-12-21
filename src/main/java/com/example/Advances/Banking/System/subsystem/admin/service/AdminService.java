package com.example.Advances.Banking.System.subsystem.admin.service;

import com.example.Advances.Banking.System.patterns.behavioral.state.BankAccount;

public interface AdminService {
    BankAccount createAccount(String accountId, String ownerName, String type);
    boolean changeState(String accountId, String newState);
}