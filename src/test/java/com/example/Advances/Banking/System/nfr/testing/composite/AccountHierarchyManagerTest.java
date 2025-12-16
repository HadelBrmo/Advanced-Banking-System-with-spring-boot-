package com.example.Advances.Banking.System.nfr.testing.composite;

import com.example.Advances.Banking.System.patterns.structural.composite.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AccountHierarchyManager Class Tests - Complete")
class AccountHierarchyManagerCompleteTest {

    private AccountHierarchyManager manager;
    private AccountGroup rootGroup;
    private IndividualAccount account1;
    private IndividualAccount account2;

    @BeforeEach
    void setUp() {
        rootGroup = new AccountGroup("المجموعة الرئيسية", "ROOT");
        account1 = new IndividualAccount("حساب1", "ACC-001", 1000.0);
        account2 = new IndividualAccount("حساب2", "ACC-002", 2000.0);
        rootGroup.add(account1);
        rootGroup.add(account2);
        manager = new AccountHierarchyManager(rootGroup);
    }



    @Nested
    @DisplayName("Deposit Operations")
    class DepositTests {

        @Test
        @DisplayName("Deposit To All with Empty Group")
        void depositToAll_WithEmptyGroup_ShouldHandleGracefully() {
            AccountGroup emptyGroup = new AccountGroup("فارغة", "EMPTY");
            AccountHierarchyManager emptyManager = new AccountHierarchyManager(emptyGroup);

            assertDoesNotThrow(() -> {
                emptyManager.depositToAll(1000.0);
            }, "Deposit to empty group should not throw exception");

            assertEquals(0.0, emptyManager.getTotalBalance(), 0.001);
        }
    }

    @Nested
    @DisplayName("Withdraw Operations")
    class WithdrawTests {

        @Test
        @DisplayName("Withdraw From All with Nested Groups")
        void withdrawFromAll_WithNestedGroups_ShouldWork() {
            AccountGroup subGroup = new AccountGroup("فرعية", "SUB");
            IndividualAccount account3 = new IndividualAccount("حساب3", "ACC-003", 1000.0);
            subGroup.add(account3);
            rootGroup.add(subGroup);


            assertTrue(manager.withdrawFromAll(2000.0));

            assertEquals(2000.0, manager.getTotalBalance(), 0.001);
        }

        @Test
        @DisplayName("Withdraw From All with Complex Hierarchy")
        void withdrawFromAll_WithComplexHierarchy() {

            AccountGroup level1 = new AccountGroup("المستوى 1", "L1");
            AccountGroup level2 = new AccountGroup("المستوى 2", "L2");
            AccountGroup level3 = new AccountGroup("المستوى 3", "L3");

            level3.add(new IndividualAccount("عميق 1", "DEEP-1", 500.0));
            level3.add(new IndividualAccount("عميق 2", "DEEP-2", 500.0));
            level2.add(level3);
            level2.add(new IndividualAccount("متوسط", "MID", 1000.0));
            level1.add(level2);
            level1.add(new IndividualAccount("سطح", "TOP", 1000.0));

            AccountHierarchyManager complexManager = new AccountHierarchyManager(level1);


            assertEquals(3000.0, complexManager.getTotalBalance(), 0.001);

            assertTrue(complexManager.withdrawFromAll(2000.0));

            assertEquals(1000.0, complexManager.getTotalBalance(), 0.001);
        }
    }

    @Nested
    @DisplayName("Hierarchy Management")
    class HierarchyManagementTests {

        @Test
        @DisplayName("Display Hierarchy - Should Not Throw")
        void displayHierarchy_ShouldNotThrowException() {
            assertDoesNotThrow(() -> {
                manager.displayHierarchy();
            }, "Display hierarchy should work without exceptions");
        }

        @Test
        @DisplayName("Add Sub Account")
        void addSubAccount_ShouldAddToRoot() {
            IndividualAccount newAccount = new IndividualAccount("جديد", "ACC-004", 4000.0);

            manager.addSubAccount(newAccount);

            assertEquals(7000.0, manager.getTotalBalance(), 0.001); // 3000 + 4000
            assertEquals(3, rootGroup.getAccountCount());
        }

        @Test
        @DisplayName("Remove Sub Account")
        void removeSubAccount_ShouldRemoveFromRoot() {
            IndividualAccount newAccount = new IndividualAccount("جديد", "ACC-004", 4000.0);
            manager.addSubAccount(newAccount);

            manager.removeSubAccount(newAccount);

            assertEquals(3000.0, manager.getTotalBalance(), 0.001);
            assertEquals(2, rootGroup.getAccountCount());
        }

        @Test
        @DisplayName("Remove Non-Existent Account")
        void removeNonExistentAccount_ShouldDoNothing() {
            IndividualAccount nonExistent = new IndividualAccount("غير موجود", "GHOST-001", 9999.0);

            double initialBalance = manager.getTotalBalance();
            int initialCount = rootGroup.getAccountCount();

            manager.removeSubAccount(nonExistent);

            assertEquals(initialBalance, manager.getTotalBalance(), 0.001);
            assertEquals(initialCount, rootGroup.getAccountCount());
        }

        @Test
        @DisplayName("Add and Remove Multiple Times")
        void addAndRemoveMultipleTimes() {
            IndividualAccount tempAccount = new IndividualAccount("مؤقت", "TEMP-001", 500.0);

            manager.addSubAccount(tempAccount);
            assertEquals(3500.0, manager.getTotalBalance(), 0.001);

            manager.removeSubAccount(tempAccount);
            assertEquals(3000.0, manager.getTotalBalance(), 0.001);

            manager.addSubAccount(tempAccount);
            assertEquals(3500.0, manager.getTotalBalance(), 0.001);

            manager.removeSubAccount(tempAccount);
            assertEquals(3000.0, manager.getTotalBalance(), 0.001);
        }
    }

    @Test
    @DisplayName("Integration Test - Full Scenario")
    void integrationTest_FullScenario() {
        System.out.println("\n=== سيناريو كامل لإدارة الهرمية ===");

        System.out.println("1. الهيكل الابتدائي:");
        manager.displayHierarchy();

        System.out.println("\n2. بعد الإيداع 600 على الكل:");
        manager.depositToAll(600.0);
        manager.displayHierarchy();

        System.out.println("\n3. بعد السحب 1000 من الكل:");
        manager.withdrawFromAll(1000.0);
        manager.displayHierarchy();

        System.out.println("\n4. بعد إضافة حساب جديد برصيد 4000:");
        IndividualAccount newAccount = new IndividualAccount("استثمار", "INV-001", 4000.0);
        manager.addSubAccount(newAccount);
        manager.displayHierarchy();

        System.out.println("\n5. بعد إزالة الحساب الجديد:");
        manager.removeSubAccount(newAccount);
        manager.displayHierarchy();

        System.out.println("\n=== انتهاء السيناريو ===");

        double finalBalance = manager.getTotalBalance();
        assertTrue(finalBalance > 0, "يجب أن يكون الرصيد النهائي موجباً");
        System.out.println("الرصيد النهائي: " + finalBalance);
    }
}