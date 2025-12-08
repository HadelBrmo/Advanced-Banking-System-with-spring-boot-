package com.example.Advances.Banking.System.accounts.AccountGroup.Entity;

import com.example.Advances.Banking.System.accounts.Accounts.Entity.Account;
import com.example.Advances.Banking.System.accounts.AccountComponent;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

public class AccountsGroup implements AccountComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String ownerName;

    @OneToMany
    private List<Account> accounts=new ArrayList<>();

    @Override
    public void showDetails() {
        System.out.println("Account Group for : "+ ownerName);
    }

    public  void addAccount(Account account){
        accounts.add(account);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public AccountsGroup() {}

    public AccountsGroup(String ownerName) {

        this.ownerName = ownerName;
    }


}
