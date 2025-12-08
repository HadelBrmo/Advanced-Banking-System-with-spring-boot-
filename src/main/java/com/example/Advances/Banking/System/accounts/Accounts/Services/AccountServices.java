package com.example.Advances.Banking.System.accounts.Accounts.Services;

import com.example.Advances.Banking.System.accounts.AccountFactory;
import com.example.Advances.Banking.System.accounts.Accounts.Entity.Account;
import com.example.Advances.Banking.System.accounts.Accounts.Entity.enAccountStatus;
import com.example.Advances.Banking.System.accounts.Accounts.Repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServices {

    private final AccountRepository accountRepository;

    public AccountServices(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public Account createAccount(String type) {
        Account account = AccountFactory.creatAccount(type);
        return accountRepository.save(account);
    }


    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }


    public Account closeAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setStatus(enAccountStatus.CLOSED);
        return accountRepository.save(account);
    }

    public Account updateAccount(Long id, Account updatedAccount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setType(updatedAccount.getType());
        account.setBalance(updatedAccount.getBalance());
        account.setStatus(updatedAccount.getStatus());

        return accountRepository.save(account);
    }
}