package com.example.Advances.Banking.System.patterns.creational.factory;


import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;

public interface AccountCreator {
    Account create(Customer customer, double balance);
    boolean supports(AccountType type);
}
