package com.example.Advances.Banking.System.patterns.creational.factory;

import com.example.Advances.Banking.System.core.enums.AccountStatus;
import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class InvestmentAccountCreator implements AccountCreator {

    private static final String DEFAULT_RISK_LEVEL = "MODERATE";

    @Override
    public Account create(Customer customer, double balance) {
        return createInvestmentAccount(customer, balance, DEFAULT_RISK_LEVEL);
    }

    @Override
    public boolean supports(AccountType type) {
        return AccountType.INVESTMENT.equals(type);
    }

    public Account createInvestmentAccount(Customer customer, double balance, String riskLevel) {
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

    //استثمار محافظ
    public Account createConservativeInvestment(Customer customer, double balance) {
        return createInvestmentAccount(customer, balance, "LOW");
    }

   // استثمار عدواني
    public Account createAggressiveInvestment(Customer customer, double balance) {
        Account account = createInvestmentAccount(customer, balance, "HIGH");
        account.setMinBalance(5000.0);
        System.out.println("🚀 Created AGGRESSIVE investment account");
        return account;
    }
}