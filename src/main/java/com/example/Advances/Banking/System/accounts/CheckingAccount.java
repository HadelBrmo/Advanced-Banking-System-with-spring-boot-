package com.example.Advances.Banking.System.accounts;

public class CheckingAccount  extends  Account{

    public CheckingAccount(String status, double v) {
        super("checking", 0.0);
    }

    public CheckingAccount() {
        super();
    }

    @Override
    public double calculateInterest() {
        return 0.0;
    }
}
