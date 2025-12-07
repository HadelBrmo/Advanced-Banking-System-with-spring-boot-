package com.example.Advances.Banking.System.accounts;

import java.util.List;

public class AccountServices {
    private final AccountRepository accountRepository;

    public AccountServices(AccountRepository accountRepository) {

        this.accountRepository = accountRepository;
    }

    //using Factory
    public Account createAccount(String type){
        Account account=AccountFactory.creatAccount(type);
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public void closeAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setStatus("closed");
        accountRepository.save(account);
    }
}

