package com.example.Advances.Banking.System.patterns.creational.factory;

import com.example.Advances.Banking.System.core.enums.AccountStatus;
import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;

public class AccountFactoryImpl implements AccountFactory {


    @Override
    public Account createAccount(AccountType type, Customer customer, double balance) {
        switch (type) {
            case SAVINGS:
                return new SavingsAccountFactory();
            case CHECKING:
                return new CheckingAccountFactory();
            case LOAN:
                return new LoanAccountFactory();
            case INVESTMENT:
                return new InvestmentAccountFactory();
            default:
                throw new IllegalArgumentException("Unknown account type: " + type);
        }
    }
}
