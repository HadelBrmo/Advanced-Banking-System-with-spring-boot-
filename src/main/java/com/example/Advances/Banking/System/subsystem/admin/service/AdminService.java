package com.example.Advances.Banking.System.subsystem.admin.service;

public interface AdminService {
    void createAccount(String accountId, String ownerName, String type);
    void changeState(String accountId, String newState);
}