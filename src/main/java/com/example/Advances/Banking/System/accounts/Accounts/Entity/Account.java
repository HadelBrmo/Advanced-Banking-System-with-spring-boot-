package com.example.Advances.Banking.System.accounts.Accounts.Entity;

import com.example.Advances.Banking.System.accounts.AccountComponent;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Account implements AccountComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;            //اما جاري و اما توفير
    private double balance;

    @Enumerated(EnumType.STRING)
    private enAccountStatus status;

    public Account() {}

    public Account(enAccountStatus status, double balance) {
        this.status = status;
        this.balance = balance;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public enAccountStatus getStatus() { return status; }

    public void setStatus(enAccountStatus status) {
        this.status = status;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        if(amount <= balance) {
            this.balance -= amount;
        } else {
            System.out.println("you can't withdraw, please check your balance");
        }
    }

    @Override
    public void showDetails() {
        System.out.println("Account : " + type + " Balance : " + balance + " Status : " + status);
    }

    public abstract double calculateInterest();
}