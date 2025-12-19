package com.example.Advances.Banking.System.core.enums;

public enum TicketStatus {
    OPEN("مفتوحة"),
    IN_PROGRESS("قيد المعالجة"),
    RESOLVED("تم الحل"),
    CLOSED("مغلقة"),
    ESCALATED("محولة");

    private final String arabicName;

    TicketStatus(String arabicName) {
        this.arabicName = arabicName;
    }

    public String getArabicName() {
        return arabicName;
    }
}
