package com.example.Advances.Banking.System.accounts;


import com.example.Advances.Banking.System.accounts.Accounts.AccountsTypes.CheckingAccount;
import com.example.Advances.Banking.System.accounts.Accounts.AccountsTypes.InvestmentAccount;
import com.example.Advances.Banking.System.accounts.Accounts.AccountsTypes.LoanAccount;
import com.example.Advances.Banking.System.accounts.Accounts.Entity.Account;
import com.example.Advances.Banking.System.accounts.Accounts.AccountsTypes.SavingAccount;

public class AccountFactory {

    public  static Account creatAccount(String type){
        switch (type){
            case "savings":
                return  new SavingAccount();
            case "checking":
                return new CheckingAccount();
            case "loan":
                return  new LoanAccount();
            case "investment":
                return  new InvestmentAccount();
            default:
                return null;
        }
    }
}
