package com.example.Advances.Banking.System.core.enums;

public enum TicketCategory {
    ACCOUNT_ISSUE("مشكلة في الحساب"),
    TRANSACTION_ISSUE("مشكلة في المعاملة"),
    TECHNICAL_ISSUE("مشكلة فنية"),
    FRAUD_ALERT("تنبيه احتيال"),
    GENERAL_INQUIRY("استفسار عام"),
    FEEDBACK("ملاحظات"),
    COMPLAINT("شكوى");

    private final String arabicName;

    TicketCategory(String arabicName) {
        this.arabicName = arabicName;
    }

    public String getArabicName() {
        return arabicName;
    }
}
