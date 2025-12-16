package com.example.Advances.Banking.System.nfr.testing.composite;

import com.example.Advances.Banking.System.patterns.structural.composite.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Composite Pattern - Integration Test")
class CompositePatternIntegrationTest {

    @Test
    @DisplayName("Complete Banking Hierarchy Scenario")
    void completeBankingHierarchy_ShouldWork() {
        System.out.println("\n🏦 سيناريو كامل للنظام المصرفي الهرمي:");
        System.out.println("=" .repeat(50));

        IndividualAccount salaryAccount = new IndividualAccount("راتب", "SAL-001", 15000.0);
        IndividualAccount savingsAccount = new IndividualAccount("توفير", "SAV-001", 25000.0);
        IndividualAccount investmentAccount = new IndividualAccount("استثمار", "INV-001", 50000.0);
        IndividualAccount expenseAccount = new IndividualAccount("مصروفات", "EXP-001", 5000.0);


        AccountGroup personalAccounts = new AccountGroup("الحسابات الشخصية", "PER");
        AccountGroup businessAccounts = new AccountGroup("حسابات الأعمال", "BUS");

        AccountGroup investmentPortfolio = new AccountGroup("محفظة الاستثمار", "PORT");
        AccountGroup mainPortfolio = new AccountGroup("المحفظة الرئيسية", "MAIN");


        personalAccounts.add(salaryAccount);
        personalAccounts.add(savingsAccount);

        investmentPortfolio.add(investmentAccount);

        businessAccounts.add(expenseAccount);

        mainPortfolio.add(personalAccounts);
        mainPortfolio.add(investmentPortfolio);
        mainPortfolio.add(businessAccounts);


        AccountHierarchyManager portfolioManager = new AccountHierarchyManager(mainPortfolio);


        System.out.println("\n📈 قبل العمليات:");
        portfolioManager.displayHierarchy();

        double initialBalance = portfolioManager.getTotalBalance();
        assertEquals(95000.0, initialBalance, 0.001, "إجمالي الرصيد الأولي");


        System.out.println("\n💰 بعد الإيداع:");
        portfolioManager.depositToAll(6000.0);  // 1500 لكل مجموعة (4 حسابات في المجموعات)
        portfolioManager.displayHierarchy();

        // 7. عمليات السحب
        System.out.println("\n💸 بعد السحب:");
        boolean withdrawalResult = portfolioManager.withdrawFromAll(20000.0);
        assertTrue(withdrawalResult, "يجب أن يكون السحب ناجحاً");
        portfolioManager.displayHierarchy();

        // 8. إضافة حساب جديد
        System.out.println("\n➕ إضافة حساب جديد:");
        IndividualAccount newAccount = new IndividualAccount("حساب جديد", "NEW-001", 10000.0);
        portfolioManager.addSubAccount(newAccount);
        portfolioManager.displayHierarchy();

        // 9. التحقق من الرصيد النهائي
        double finalBalance = portfolioManager.getTotalBalance();
        System.out.println("\n✅ الرصيد النهائي: " + finalBalance);
        assertTrue(finalBalance > 0, "يجب أن يكون الرصيد النهائي أكبر من الصفر");
    }

    @Test
    @DisplayName("Complex Nested Structure")
    void complexNestedStructure_ShouldWork() {
        // Given: هيكل معقد متداخل
        AccountGroup level1 = new AccountGroup("المستوى 1", "L1");
        AccountGroup level2a = new AccountGroup("المستوى 2-أ", "L2A");
        AccountGroup level2b = new AccountGroup("المستوى 2-ب", "L2B");
        AccountGroup level3 = new AccountGroup("المستوى 3", "L3");

        // بناء الهيكل
        level3.add(new IndividualAccount("حساب عميق", "DEEP-001", 1000.0));
        level2a.add(level3);
        level2a.add(new IndividualAccount("حساب متوسط", "MID-001", 2000.0));
        level2b.add(new IndividualAccount("حساب آخر", "OTH-001", 3000.0));
        level1.add(level2a);
        level1.add(level2b);

        // When
        AccountHierarchyManager manager = new AccountHierarchyManager(level1);

        // Then
        assertEquals(6000.0, manager.getTotalBalance(), 0.001);
        assertEquals(2, level1.getChildren().size());
        assertEquals(2, level2a.getChildren().size());

        // Test display
        System.out.println("\n🌳 هيكل متداخل معقد:");
        manager.displayHierarchy();
    }
}
