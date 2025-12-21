package com.example.Advances.Banking.System.core.constants;

public final class SystemConstants {
    private SystemConstants() {} // Prevent instantiation
    public static final int MAX_CONNECTIONS = 20;
    public static final int MIN_IDLE_CONNECTIONS = 10;
    public static final int CONNECTION_TIMEOUT = 30000;

    public static final double MAX_TRANSACTION_AMOUNT = 1000000.00;
    public static final double MIN_TRANSACTION_AMOUNT = 1.00;
    public static final int MAX_TRANSACTIONS_PER_DAY = 50;

    public static final double MIN_ACCOUNT_BALANCE = 0.00;
    public static final double OVERDRAFT_LIMIT = -1000.00;

    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int MAX_LOGIN_ATTEMPTS = 5;

    public static final int SESSION_TIMEOUT_MINUTES = 30;
    public static final int CACHE_EXPIRY_MINUTES = 10;
}
