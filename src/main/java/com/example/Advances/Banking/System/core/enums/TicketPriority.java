package com.example.Advances.Banking.System.core.enums;

public enum TicketPriority {
    LOW("منخفض"),
    MEDIUM("متوسط"),
    HIGH("مرتفع"),
    URGENT("عاجل");

    private final String arabicName;

    TicketPriority(String arabicName) {
        this.arabicName = arabicName;
    }

    public String getArabicName() {
        return arabicName;
    }
}