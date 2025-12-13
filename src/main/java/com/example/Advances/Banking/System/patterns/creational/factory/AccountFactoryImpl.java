package com.example.Advances.Banking.System.patterns.creational.factory;

import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AccountFactoryImpl implements AccountFactory {

    private final Map<AccountType, AccountCreator> creators;

    public AccountFactoryImpl(List<AccountCreator> creatorList) {
        this.creators = creatorList.stream()
                .collect(Collectors.toMap(
                        creator -> findSupportedType(creator),
                        creator -> creator
                ));
    }

    private AccountType findSupportedType(AccountCreator creator) {
        for (AccountType type : AccountType.values()) {
            if (creator.supports(type)) {
                return type;
            }
        }
        throw new IllegalStateException("Creator doesn't support any AccountType: " + creator.getClass());
    }

    @Override
    public Account createAccount(AccountType type, Customer customer, double balance) {
        AccountCreator creator = creators.get(type);

        if (creator == null) {
            throw new IllegalArgumentException("No creator found for account type: " + type);
        }

        return creator.create(customer, balance);
    }
}