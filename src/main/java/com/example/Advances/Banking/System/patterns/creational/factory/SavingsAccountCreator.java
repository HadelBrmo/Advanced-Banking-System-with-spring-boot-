package com.example.Advances.Banking.System.patterns.creational.factory;

import com.example.Advances.Banking.System.core.enums.AccountStatus;
import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class SavingsAccountCreator implements AccountCreator {

    @Override
    public Account create(Customer customer, double balance) {
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

    @Override
    public boolean supports(AccountType type) {
        return AccountType.SAVINGS.equals(type);
    }
}