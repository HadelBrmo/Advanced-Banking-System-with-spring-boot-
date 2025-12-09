package com.example.Advances.Banking.System.core.enums;


public enum AccountStatus {
    ACTIVE("Active", true),
    FROZEN("Frozen", false),
    SUSPENDED("Suspended", false),
    CLOSED("Closed", false),
    PENDING("Pending", false);

    private final String description;
    private final boolean canTransact;

    AccountStatus(String description, boolean canTransact) {
        this.description = description;
        this.canTransact = canTransact;
    }

    public String getDescription() {
        return description;
    }

    public boolean canTransact() {
        return canTransact;
    }
}
