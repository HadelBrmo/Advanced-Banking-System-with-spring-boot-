package com.example.Advances.Banking.System.exception;


// هذا يقابل: exception/ → AccountNotFoundException.java
public class AccountNotFoundException extends BankingException {

    public AccountNotFoundException(String accountId) {
        super(
                String.format("Account with ID %s not found", accountId),
                "ACCOUNT_NOT_FOUND",
                404
        );
    }
}
