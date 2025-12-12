//package com.example.Advances.Banking.System.nfr.testability;// AccountFactoryTest.java
//
//import com.example.Advances.Banking.System.core.model.Account;
//import com.example.Advances.Banking.System.core.model.Customer;
//import com.example.Advances.Banking.System.core.enums.AccountType;
//import com.example.Advances.Banking.System.patterns.creational.factory.SavingsAccountFactory;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//class AccountFactoryTest {
//
//    private Customer testCustomer;
//
//    @BeforeEach
//    void setUp() {
//        // تهيئة Customer قبل كل test
//        testCustomer = new Customer("Ali", "Hassan", "ali@test.com");
//        testCustomer.setPhone("+9631234567");
//    }
//
//    @Test
//    void savingsFactory_ShouldCreateAccountWithCorrectSettings() {
//        // Given
//        SavingsAccountFactory factory = new SavingsAccountFactory();
//
//        // When
//        Account account = factory.createAccount(testCustomer, 5000.0);
//
//        // Then
//        assertNotNull(account, "يجب أن لا يكون الحساب null");
//        assertEquals(AccountType.SAVINGS, account.getAccountType(), "نوع الحساب يجب أن يكون SAVINGS");
//        assertEquals(5000.0, account.getBalance(), 0.001, "الرصيد يجب أن يكون 5000");
//        assertEquals(testCustomer, account.getCustomer(), "العميل يجب أن يكون نفسه");
//
//        // إعدادات خاصة بحساب التوفير
//        assertEquals(100.0, account.getMinBalance(), 0.001, "الرصيد الأدنى يجب أن يكون 100");
//        assertEquals(1000.0, account.getMaxDailyWithdrawal(), 0.001, "الحد اليومي يجب أن يكون 1000");
//        assertFalse(account.getHasOverdraft(), "حساب التوفير لا يدعم السحب المكشوف");
//    }
//}