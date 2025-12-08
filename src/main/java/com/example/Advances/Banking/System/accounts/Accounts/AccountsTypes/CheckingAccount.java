package com.example.Advances.Banking.System.accounts.Accounts.AccountsTypes;

import com.example.Advances.Banking.System.accounts.Accounts.Entity.Account;
import com.example.Advances.Banking.System.accounts.Accounts.Entity.enAccountStatus;

public class CheckingAccount  extends Account {

    public CheckingAccount(String status, double v) {
        super(enAccountStatus.valueOf("checking"), 0.0);
    }

    public CheckingAccount() {
        super();
    }

    @Override
    public double calculateInterest() {
        return 0.0;
    }
}
