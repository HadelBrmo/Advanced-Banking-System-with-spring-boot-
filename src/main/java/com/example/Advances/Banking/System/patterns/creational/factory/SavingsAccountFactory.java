package com.example.Advances.Banking.System.patterns.creational.factory;

// SavingsAccountFactory.java
import com.example.Advances.Banking.System.core.enums.AccountStatus;
import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;

public class SavingsAccountFactory extends Account {
    public Account createAccount(Customer customer, double balance) {
        Account account = new Account();
        account.setAccountType(AccountType.SAVINGS);
        account.setCustomer(customer);
        account.setBalance(balance);
        account.setStatus(AccountStatus.ACTIVE);

        account.setMinBalance(100.0);
        account.setMaxDailyWithdrawal(1000.0);
        account.setHasOverdraft(false);

        System.out.println(" Created SAVINGS account for " + customer.getFullName());
        return account;
    }


    public Account createHighYieldSavings(Customer customer, double balance) {
        Account account = createAccount(customer, balance);
        account.setMinBalance(500.0);
        account.setMaxDailyWithdrawal(5000.0);
        System.out.println(" Created HIGH YIELD savings account");
        return account;
    }
}
