package com.example.Advances.Banking.System.nfr.testing.decorator;

import com.example.Advances.Banking.System.patterns.structural.decorator.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InsuranceDecorator Tests")
class InsuranceDecoratorTest {

    private BankAccount basicAccount;
    private InsuranceDecorator insuredAccount;

    @BeforeEach
    void setUp() {
        basicAccount = new BasicAccount("ACC123", 1000.0);
        insuredAccount = new InsuranceDecorator(basicAccount, 10000.0);
    }

    @Test
    @DisplayName("Constructor should initialize correctly")
    void constructor_ShouldInitializeCorrectly() {
        assertEquals(1000.0, insuredAccount.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Withdraw should work normally")
    void withdraw_ShouldWorkNormally() {
        boolean result = insuredAccount.withdraw(500.0);
        assertTrue(result);
        assertEquals(500.0, insuredAccount.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Get description should include insurance info")
    void getDescription_ShouldIncludeInsuranceInfo() {
        String description = insuredAccount.getDescription();
        assertTrue(description.contains("تأمين على الحساب"));
        assertTrue(description.contains("10000.0"));
    }

    @Test
    @DisplayName("Get monthly fee should include insurance fee")
    void getMonthlyFee_ShouldIncludeInsuranceFee() {
        assertEquals(3.0, insuredAccount.getMonthlyFee(), 0.001);
    }

    @Test
    @DisplayName("File claim within coverage should succeed")
    void fileClaim_WithinCoverage_ShouldSucceed() {
        insuredAccount.fileClaim(5000.0);
        assertEquals(6000.0, insuredAccount.getBalance(), 0.001);
    }

    @Test
    @DisplayName("File claim exceeding coverage should fail")
    void fileClaim_ExceedingCoverage_ShouldFail() {
        insuredAccount.fileClaim(15000.0);
        assertEquals(1000.0, insuredAccount.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Multiple claims within coverage")
    void multipleClaims_WithinCoverage() {
        insuredAccount.fileClaim(3000.0);
        assertEquals(4000.0, insuredAccount.getBalance(), 0.001);

        insuredAccount.fileClaim(2000.0);
        assertEquals(6000.0, insuredAccount.getBalance(), 0.001);

        insuredAccount.withdraw(1000.0);
        assertEquals(5000.0, insuredAccount.getBalance(), 0.001);
    }
}
