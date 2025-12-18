// src/main/java/com/example/Advances/Banking/System/patterns/creational/factory/BusinessAccountCreator.java
package com.example.Advances.Banking.System.patterns.creational.factory;

import com.example.Advances.Banking.System.core.enums.AccountStatus;
import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class BusinessAccountCreator implements AccountCreator {

    @Override
    public Account create(Customer customer, double balance) {
        Account account = new Account();
        account.setAccountType(AccountType.BUSINESS);
        account.setCustomer(customer);
        account.setBalance(balance);
        account.setStatus(AccountStatus.ACTIVE);

        account.setMinBalance(1000.0);
        account.setMaxDailyWithdrawal(10000.0);
        account.setHasOverdraft(true);
        account.setOverdraftLimit(5000.0);
        account.setRiskLevel("MEDIUM");

        System.out.println("🏢 Created BUSINESS account for " + customer.getFullName());
        System.out.println("   Max Daily Withdrawal: $" + account.getMaxDailyWithdrawal());
        return account;
    }

    @Override
    public boolean supports(AccountType type) {
        return AccountType.BUSINESS.equals(type);
    }
}