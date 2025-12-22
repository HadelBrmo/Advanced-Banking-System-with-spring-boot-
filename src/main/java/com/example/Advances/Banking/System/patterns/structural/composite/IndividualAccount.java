package com.example.Advances.Banking.System.patterns.structural.composite;

import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.enums.AccountType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class IndividualAccount implements AccountComponent {

    private final Account account;

    public IndividualAccount(Account account) {
        this.account = account;
    }

    public IndividualAccount(String accountName, String accountNumber, double initialBalance) {
        this.account = new Account();
        this.account.setAccountNumber(accountNumber);
        this.account.setBalance(initialBalance);

    }

    @Override
    public String getAccountNumber() {
        return account.getAccountNumber();
    }

    @Override
    public String getAccountName() {
        return "حساب فردي - " + account.getAccountNumber();
    }

    @Override
    public double getBalance() {
        return account.getBalance();
    }

    @Override
    public void deposit(double amount) {
        account.deposit(amount);
        System.out.println("Depositing  " + amount + " to the account " + getAccountNumber());
    }

    @Override
    public boolean withdraw(double amount) {
        if (account.canWithdraw(amount)) {
            account.withdraw(amount);
            System.out.println("WithDrawing" + amount + " from account " + getAccountNumber());
            return true;
        }
        System.out.println("you can't withdraw from this account" + getAccountNumber());
        return false;
    }

    @Override
    public void add(AccountComponent component) {
        throw new UnsupportedOperationException("الحساب الفردي لا يقبل حسابات فرعية");
    }

    @Override
    public void remove(AccountComponent component) {
        throw new UnsupportedOperationException("الحساب الفردي لا يحتوي حسابات فرعية");
    }

    @Override
    public List<AccountComponent> getChildren() {
        return new ArrayList<>();
    }

    @Override
    public void display(int indent) {
        String spaces = " ".repeat(indent);
        System.out.println(spaces + "IndividualAccount :  " + getAccountNumber() +
                " Balance" + getBalance() + ")");
    }

}
