package com.example.Advances.Banking.System.accounts;

public class AccountFactory {

    public  static Account creatAccount(String type){
        switch (type){
            case "savings":
                return  new SavingAccount();
            case "checking":
                return new CheckingAccount();
            default:
                return null;
        }
    }
}
