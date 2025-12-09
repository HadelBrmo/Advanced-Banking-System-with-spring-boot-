package com.example.Advances.Banking.System.exception;


public class InsufficientFundsException extends BankingException {

    public InsufficientFundsException(String accountNumber, double balance, double requested) {
        super(
                String.format("Account %s has insufficient funds. Balance: %.2f, Requested: %.2f",
                        accountNumber, balance, requested),
                "INSUFFICIENT_FUNDS",
                400
        );
    }
}
