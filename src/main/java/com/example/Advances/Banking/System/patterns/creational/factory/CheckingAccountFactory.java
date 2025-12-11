package com.example.Advances.Banking.System.patterns.creational.factory;


import com.example.Advances.Banking.System.core.enums.AccountStatus;
import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;

public class CheckingAccountFactory extends Account {

    public Account createAccount(Customer customer, double balance) {
        Account account = new Account();
        account.setAccountType(AccountType.CHECKING);
        account.setCustomer(customer);
        account.setBalance(balance);
        account.setStatus(AccountStatus.ACTIVE);


        account.setMinBalance(0.0);
        account.setMaxDailyWithdrawal(2000.0);
        account.setHasOverdraft(true);
        account.setOverdraftLimit(500.0);

        System.out.println("🏦 Created CHECKING account for " + customer.getFullName());
        return account;
    }


    public Account createPremiumChecking(Customer customer, double balance) {
        Account account = createAccount(customer, balance);
        account.setOverdraftLimit(2000.0);
        account.setMaxDailyWithdrawal(5000.0);
        System.out.println(" Created PREMIUM checking account");
        return account;
    }
}
