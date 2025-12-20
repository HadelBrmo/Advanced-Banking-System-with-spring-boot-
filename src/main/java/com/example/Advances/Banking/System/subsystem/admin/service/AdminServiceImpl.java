package com.example.Advances.Banking.System.subsystem.admin.service;

import com.example.Advances.Banking.System.patterns.behavioral.state.BankAccount;
import com.example.Advances.Banking.System.patterns.behavioral.state.FrozenState;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Override
    public BankAccount createAccount(String accountId, String ownerName, String type) {
        BankAccount account = new BankAccount();
        account.setBalance(0);
        return account;
    }

    @Override
    public boolean changeState(String accountId, String newState) {
        BankAccount account = new BankAccount();
        account.setState(new FrozenState());
        return true;
    }
}