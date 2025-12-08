package com.example.Advances.Banking.System.accounts.Accounts.Entity;

public enum enAccountStatus {
    ACTIVE,
    FROZEN,                      // الحساب مجمد بسبب مشكلة امنية
    SUSPENDED,                   // الحساب موقوف مؤقتا بسبب مخالفة
    CLOSED
}
