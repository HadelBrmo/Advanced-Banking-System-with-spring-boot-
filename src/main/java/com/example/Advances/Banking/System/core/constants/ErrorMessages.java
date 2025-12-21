package com.example.Advances.Banking.System.core.constants;


public final class ErrorMessages {
    private ErrorMessages() {}

    // Account Errors
    public static final String ACCOUNT_NOT_FOUND = "Account not found: %s";
    public static final String INSUFFICIENT_FUNDS = "Insufficient funds in account: %s";
    public static final String ACCOUNT_INACTIVE = "Account is inactive: %s";
    public static final String INVALID_ACCOUNT_TYPE = "Invalid account type: %s";

    // Transaction Errors
    public static final String INVALID_TRANSACTION_AMOUNT = "Invalid transaction amount: %.2f";
    public static final String DAILY_LIMIT_EXCEEDED = "Daily transaction limit exceeded";
    public static final String TRANSACTION_FAILED = "Transaction failed: %s";

    // Customer Errors
    public static final String CUSTOMER_NOT_FOUND = "Customer not found: %s";
    public static final String DUPLICATE_CUSTOMER = "Customer already exists with ID: %s";

    // Validation Errors
    public static final String INVALID_INPUT = "Invalid input: %s";
    public static final String REQUIRED_FIELD = "Field is required: %s";

    // System Errors
    public static final String DATABASE_CONNECTION_FAILED = "Database connection failed";
    public static final String SERVICE_UNAVAILABLE = "Service temporarily unavailable";

    // Security Errors
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized access attempt";
    public static final String INVALID_CREDENTIALS = "Invalid credentials provided";
}
