package com.example.Advances.Banking.System.patterns.creational.factory;

import com.example.Advances.Banking.System.core.enums.AccountStatus;
import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;

public class AccountFactoryImpl implements  AccountFactory{
    @Override
    public Account createAccount(AccountType type, Customer customer, double balance) {
        Account account = new Account();
        account.setCustomer(customer);
        account.setBalance(balance);
        account.setStatus(AccountStatus.ACTIVE);
        account.setAccountType(type);
        switch (type) {
            case SAVINGS:
                account.setMinBalance(100.0);
                account.setMaxDailyWithdrawal(2000.0);
                break;

            case CHECKING:
                account.setMinBalance(0.0);
                account.setHasOverdraft(true);
                account.setOverdraftLimit(500.0);
                break;

            case LOAN:
                account.setMinBalance(-10000.0);
                account.setLoanTermMonths(60);
                break;

            case INVESTMENT:
                account.setMinBalance(1000.0);
                account.setRiskLevel("MEDIUM");
                break;
        }
        return account;
    }

}
