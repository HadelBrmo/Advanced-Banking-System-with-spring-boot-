package com.example.Advances.Banking.System.patterns.creational.factory;

import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;

//"Support multiple account types"
public interface AccountFactory {

    Account createAccount(AccountType type, Customer customer,double balance);
}
