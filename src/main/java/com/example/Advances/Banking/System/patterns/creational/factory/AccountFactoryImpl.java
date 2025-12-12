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
                SavingsAccountFactory factory = new SavingsAccountFactory();
                return SavingsAccountFactory.createAccount(customer, balance);
            case CHECKING:
                return CheckingAccountFactory.createAccount(customer, balance);
            case LOAN:
                return  LoanAccountFactory.createAccount(customer, balance);
            case INVESTMENT:
                return  InvestmentAccountFactory.createAccount(customer, balance);
            default:
                throw new IllegalArgumentException("Unknown account type: " + type);
        }
    }
}
