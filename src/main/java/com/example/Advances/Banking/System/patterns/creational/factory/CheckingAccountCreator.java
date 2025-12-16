package com.example.Advances.Banking.System.patterns.creational.factory;

import com.example.Advances.Banking.System.core.enums.AccountStatus;
import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CheckingAccountCreator implements AccountCreator {

    @Override
    public Account create(Customer customer, double balance) {
        Account account = new Account();
        account.setAccountType(AccountType.CHECKING);
        account.setCustomer(customer);
        account.setBalance(balance);
        account.setStatus(AccountStatus.ACTIVE);

        account.setMinBalance(0.0);
        account.setMaxDailyWithdrawal(2000.0);
        account.setHasOverdraft(true);
        account.setOverdraftLimit(500.0);

        System.out.println(" Created CHECKING account for " + customer.getFullName());
        return account;
    }

    @Override
    public boolean supports(AccountType type) {
        return AccountType.CHECKING.equals(type);
    }

    public Account createPremiumChecking(Customer customer, double balance) {
        Account account = new Account();
        account.setAccountType(AccountType.CHECKING);
        account.setCustomer(customer);
        account.setBalance(balance);
        account.setStatus(AccountStatus.ACTIVE);

        // Premium-specific settings
        account.setMinBalance(0.0);
        account.setMaxDailyWithdrawal(5000.0); // Higher limit for premium
        account.setHasOverdraft(true);
        account.setOverdraftLimit(2000.0); // Higher overdraft for premium

        System.out.println(" Created PREMIUM checking account for " + customer.getFullName());
        return account;
    }
}