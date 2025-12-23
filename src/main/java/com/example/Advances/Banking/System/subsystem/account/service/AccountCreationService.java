package com.example.Advances.Banking.System.subsystem.account.service;

import com.example.Advances.Banking.System.core.enums.AccountStatus;
import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;
import com.example.Advances.Banking.System.subsystem.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AccountCreationService {

    private final AccountRepository accountRepository;

    public AccountCreationService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public Account createAccount(String accountNumber, Customer customer,
                                 AccountType type, double initialBalance) {

        System.out.println("➕ Creating new account: " + accountNumber);

        if (accountRepository.findByAccountNumber(accountNumber).isPresent()) {
            throw new IllegalArgumentException("Account number already exists");
        }

        // إنشاء الحساب
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setCustomer(customer);
        account.setAccountType(type);
        account.setBalance(initialBalance);
        account.setStatus(AccountStatus.ACTIVE);
        account.setCreatedAt(new Date());

        Account savedAccount = accountRepository.save(account);

        System.out.println("✅ Account created successfully: " + accountNumber);
        return savedAccount;
    }
}