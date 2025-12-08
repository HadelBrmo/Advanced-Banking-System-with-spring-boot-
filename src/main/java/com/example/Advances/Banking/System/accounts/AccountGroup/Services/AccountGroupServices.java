package com.example.Advances.Banking.System.accounts.AccountGroup.Services;

import com.example.Advances.Banking.System.accounts.AccountGroup.Entity.AccountsGroup;
import com.example.Advances.Banking.System.accounts.AccountGroup.Repository.AccountGroupsRepository;
import com.example.Advances.Banking.System.accounts.Accounts.Entity.Account;
import com.example.Advances.Banking.System.accounts.Accounts.Repository.AccountRepository;

import java.util.List;

public class AccountGroupServices {
    private  final AccountGroupsRepository accountGroupsRepository;
    private  final AccountRepository accountRepository;

    public AccountGroupServices(AccountGroupsRepository accountGroupsRepository, AccountRepository accountRepository) {
        this.accountGroupsRepository = accountGroupsRepository;
        this.accountRepository = accountRepository;
    }

    public AccountsGroup createGroup(String ownerName){
        AccountsGroup accountsGroup=new AccountsGroup(ownerName);
        return  accountGroupsRepository.save(accountsGroup);
    }

    public AccountsGroup addAccountToGroup(Long groupId, Long accountId) {
        AccountsGroup group = accountGroupsRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        group.addAccount(account);
        return accountGroupsRepository.save(group);
    }

    public List<AccountsGroup> getAllGroups() {
        return accountGroupsRepository.findAll();
    }
}
