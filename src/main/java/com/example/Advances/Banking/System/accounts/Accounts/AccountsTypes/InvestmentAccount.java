package com.example.Advances.Banking.System.accounts.Accounts.AccountsTypes;

import com.example.Advances.Banking.System.accounts.Accounts.Entity.Account;
import com.example.Advances.Banking.System.accounts.Accounts.Entity.enAccountStatus;

public class InvestmentAccount extends Account {

    public InvestmentAccount() {
        super(enAccountStatus.ACTIVE, 0.0);
        setType("investment");
    }

    @Override
    public double calculateInterest() {
        return getBalance() * 0.08;
    }
}
