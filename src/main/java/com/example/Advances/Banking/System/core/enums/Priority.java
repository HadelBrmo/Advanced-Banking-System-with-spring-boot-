package com.example.Advances.Banking.System.core.enums;


public enum Priority {
    URGENT(4),
    HIGH(3),
    MEDIUM(2),
    LOW(1);

    private final int value;
    Priority(int value) { this.value = value; }
    public int getValue() { return value; }
}
