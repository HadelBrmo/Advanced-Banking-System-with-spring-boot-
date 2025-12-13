package com.example.Advances.Banking.System.patterns.creational.factory;

import com.example.Advances.Banking.System.core.enums.AccountStatus;
import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class LoanAccountCreator implements AccountCreator {

    private static final int DEFAULT_TERM_MONTHS = 12;      //القيمة الاقترتضية 12 شهر

    @Override
    public Account create(Customer customer, double balance) {
        return createLoanAccount(customer, balance, DEFAULT_TERM_MONTHS);
    }

    @Override
    public boolean supports(AccountType type) {
        return AccountType.LOAN.equals(type);
    }

    public Account createLoanAccount(Customer customer, double loanAmount, int termMonths) {
        Account account = new Account();
        account.setAccountType(AccountType.LOAN);
        account.setCustomer(customer);
        account.setBalance(loanAmount);
        account.setStatus(AccountStatus.ACTIVE);

        account.setMinBalance(-loanAmount);
        account.setLoanTermMonths(termMonths);
        account.setHasOverdraft(false);

        System.out.println(" Created LOAN account - Amount: $" + loanAmount +
                ", Term: " + termMonths + " months");
        return account;
    }

    public Account createShortTermLoan(Customer customer, double amount) {
        return createLoanAccount(customer, amount, 6);
    }
   //قرض عقاري (رهن) 20 سنة ==240 شهر
    public Account createMortgageLoan(Customer customer, double amount) {
        Account account = createLoanAccount(customer, amount, 240);
        account.setLoanTermMonths(240);
        System.out.println(" Created MORTGAGE loan");
        return account;
    }
}