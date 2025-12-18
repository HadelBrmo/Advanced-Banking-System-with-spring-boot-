// src/main/java/com/example/Advances/Banking/System/patterns/creational/factory/StudentAccountCreator.java
package com.example.Advances.Banking.System.patterns.creational.factory;

import com.example.Advances.Banking.System.core.enums.AccountStatus;
import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class StudentAccountCreator implements AccountCreator {

    @Override
    public Account create(Customer customer, double balance) {
        Account account = new Account();
        account.setAccountType(AccountType.STUDENT);
        account.setCustomer(customer);
        account.setBalance(balance);
        account.setStatus(AccountStatus.ACTIVE);

        account.setMinBalance(0.0);
        account.setMaxDailyWithdrawal(500.0);
        account.setHasOverdraft(true);
        account.setOverdraftLimit(100.0);
        account.setLoanTermMonths(0);

        System.out.println("🎓 Created STUDENT account for " + customer.getFullName());
        System.out.println("   Max Daily Withdrawal: $" + account.getMaxDailyWithdrawal());
        return account;
    }

    @Override
    public boolean supports(AccountType type) {
        return AccountType.STUDENT.equals(type);
    }
}