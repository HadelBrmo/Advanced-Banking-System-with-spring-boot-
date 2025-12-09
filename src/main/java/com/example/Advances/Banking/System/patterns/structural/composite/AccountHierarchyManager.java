package com.example.Advances.Banking.System.patterns.structural.composite;


public class AccountHierarchyManager {

    private AccountComponent rootAccount;

    public AccountHierarchyManager(AccountComponent rootAccount) {
        this.rootAccount = rootAccount;
    }

    public void displayHierarchy() {
        System.out.println("\n📊 الهيكل الهرمي للحسابات:");
        System.out.println("=" .repeat(40));
        rootAccount.display(0);
        System.out.println("=" .repeat(40));
    }

    public double getTotalBalance() {
        return rootAccount.getBalance();
    }

    public void depositToAll(double amount) {
        rootAccount.deposit(amount);
    }

    public boolean withdrawFromAll(double amount) {
        return rootAccount.withdraw(amount);
    }

    public void addSubAccount(AccountComponent account) {
        rootAccount.add(account);
    }


    public void removeSubAccount(AccountComponent account) {
        rootAccount.remove(account);
    }
}
