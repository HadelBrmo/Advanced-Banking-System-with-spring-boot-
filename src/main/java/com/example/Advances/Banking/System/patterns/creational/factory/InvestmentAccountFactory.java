package com.example.Advances.Banking.System.patterns.creational.factory;


import com.example.Advances.Banking.System.core.enums.AccountStatus;
import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;

public class InvestmentAccountFactory  {
    public static Account createAccount(Customer customer, double balance) {
        return createInvestmentAccount(customer, balance, "MODERATE");
    }

    public static Account createInvestmentAccount(Customer customer, double balance, String riskLevel) {
        Account account = new Account();
        account.setAccountType(AccountType.INVESTMENT);
        account.setCustomer(customer);
        account.setBalance(balance);
        account.setStatus(AccountStatus.ACTIVE);

        account.setMinBalance(1000.0);
        account.setRiskLevel(riskLevel);
        account.setHasOverdraft(false);

        System.out.println(" Created INVESTMENT account - Risk: " + riskLevel +
                ", Balance: $" + balance);
        return account;
    }


    public Account createConservativeInvestment(Customer customer, double balance) {
        return createInvestmentAccount(customer, balance, "LOW");
    }

    public Account createAggressiveInvestment(Customer customer, double balance) {
        Account account = createInvestmentAccount(customer, balance, "HIGH");
        account.setMinBalance(5000.0);
        System.out.println("🚀 Created AGGRESSIVE investment account");
        return account;
    }
}
