package com.example.Advances.Banking.System.patterns.structural.composite;


import java.util.ArrayList;
import java.util.List;


public class AccountGroup implements AccountComponent {

    private final String groupName;
    private final String groupCode;
    private final List<AccountComponent> accounts = new ArrayList<>();

    public AccountGroup(String groupName, String groupCode) {
        this.groupName = groupName;
        this.groupCode = groupCode;
    }

    @Override
    public String getAccountNumber() {
        return "GRP-" + groupCode;
    }

    @Override
    public String getAccountName() {
        return "Group  " + groupName;
    }

    @Override
    public double getBalance() {
        return accounts.stream()
                .mapToDouble(AccountComponent::getBalance)
                .sum();
    }

    @Override
    public void deposit(double amount) {
        if (accounts.isEmpty()) {
            System.out.println("you can't do that , List is free, There is no account");
            return;
        }

        double share = amount / accounts.size();
        for (AccountComponent account : accounts) {
            account.deposit(share);
        }
        System.out.println("تم توزيع إيداع " + amount + " على مجموعة " + groupName);
    }

    @Override
    public boolean withdraw(double amount) {
        if (getBalance() >= amount) {
            for (AccountComponent account : accounts) {
                if (account.getBalance() >= amount) {
                    return account.withdraw(amount);
                }
            }
            double remaining = amount;
            for (AccountComponent account : accounts) {
                if (remaining <= 0) break;
                if (account.getBalance() > 0) {
                    double withdrawAmount = Math.min(account.getBalance(), remaining);
                    account.withdraw(withdrawAmount);
                    remaining -= withdrawAmount;
                }
            }
            System.out.println("تم سحب " + amount + " من مجموعة " + groupName);
            return true;
        }
        System.out.println("رصيد المجموعة غير كافي للسحب");
        return false;
    }

    @Override
    public void add(AccountComponent component) {
        accounts.add(component);
        System.out.println("تم إضافة حساب إلى مجموعة " + groupName);
    }

    @Override
    public void remove(AccountComponent component) {
        accounts.remove(component);
        System.out.println("تم إزالة حساب من مجموعة " + groupName);
    }

    @Override
    public List<AccountComponent> getChildren() {
        return new ArrayList<>(accounts);
    }

    @Override
    public void display(int indent) {
        String spaces = " ".repeat(indent);
        System.out.println(spaces + "┌─ مجموعة: " + groupName +
                " [" + groupCode + "]");
        System.out.println(spaces + "│  الرصيد الإجمالي: " + getBalance());
        System.out.println(spaces + "│  عدد الحسابات: " + accounts.size());

        for (int i = 0; i < accounts.size(); i++) {
            AccountComponent account = accounts.get(i);
            if (i == accounts.size() - 1) {
                System.out.print(spaces + "└──");
                account.display(indent + 4);
            } else {
                System.out.print(spaces + "├──");
                account.display(indent + 4);
            }
        }
    }

    public int getAccountCount() {
        return accounts.size();
    }

    public String getGroupName() {
        return groupName;
    }
}
