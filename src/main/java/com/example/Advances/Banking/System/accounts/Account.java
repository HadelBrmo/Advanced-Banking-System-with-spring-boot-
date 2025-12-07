package com.example.Advances.Banking.System.accounts;

import jakarta.persistence.*;

//قاعدة البيانات
@Entity
//كلاس الاب في اولاد رح تورث منو
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

public abstract class Account{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String type;
    private double balance;
    private String status;

    public Account(String status, double v) {
        this.status = "active";
        this.balance=0.0;
    }

    public Account() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public  void deposit(double amount){
        this.balance+=amount;
    }

    public  void withdraw(double amount){
        if(amount<=balance){
            this.balance-=amount;
        }
        else{
            System.out.println("you can't withDraw, please Check your balance");
        }
    }

    //يحسب الفوائد
    public  abstract double calculateInterest();
}
