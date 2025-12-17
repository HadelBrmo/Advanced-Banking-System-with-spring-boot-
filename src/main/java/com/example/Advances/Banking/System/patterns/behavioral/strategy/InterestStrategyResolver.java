package com.example.Advances.Banking.System.patterns.behavioral.strategy;

import com.example.Advances.Banking.System.core.enums.AccountType;
import org.springframework.stereotype.Component;

@Component
public class InterestStrategyResolver {

    public InterestStrategy resolve(AccountType type) {
        switch (type) {
            case SAVINGS: return new SavingsInterestStrategy();
            case CHECKING: return new CheckingInterestStrategy();
            case INVESTMENT: return new CompoundInterestStrategy();
            case LOAN: return new LoanInterestStrategy();
            default: return new SimpleInterestStrategy(0.01);
        }
    }
}