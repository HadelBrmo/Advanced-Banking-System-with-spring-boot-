package com.example.Advances.Banking.System.accounts.Accounts.AccountsTypes;
import com.example.Advances.Banking.System.accounts.Accounts.Entity.Account;
import com.example.Advances.Banking.System.accounts.Accounts.Entity.enAccountStatus;

public class LoanAccount extends Account {

    public LoanAccount() {
        super(enAccountStatus.ACTIVE, 0.0);
        setType("loan");
    }

    @Override
    public double calculateInterest() {
        return getBalance() * 0.05;
    }
}
