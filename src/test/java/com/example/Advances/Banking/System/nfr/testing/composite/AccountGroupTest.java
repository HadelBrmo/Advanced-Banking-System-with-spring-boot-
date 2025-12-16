package com.example.Advances.Banking.System.nfr.testing.composite;

import com.example.Advances.Banking.System.patterns.structural.composite.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AccountGroup Class Tests")
class AccountGroupTest {

    private AccountGroup group;
    private IndividualAccount account1;
    private IndividualAccount account2;
    private IndividualAccount account3;

    @BeforeEach
    void setUp() {
        group = new AccountGroup("مجموعة الأعمال", "BUS-001");
        account1 = new IndividualAccount("حساب1", "ACC-001", 1000.0);
        account2 = new IndividualAccount("حساب2", "ACC-002", 2000.0);
        account3 = new IndividualAccount("حساب3", "ACC-003", 3000.0);
    }

    @Test
    @DisplayName("Constructor Tests")
    void constructor_ShouldInitializeCorrectly() {
        assertEquals("GRP-BUS-001", group.getAccountNumber());
        assertEquals("Group  مجموعة الأعمال", group.getAccountName());
        assertEquals(0.0, group.getBalance(), 0.001);
        assertTrue(group.getChildren().isEmpty());
        assertEquals(0, group.getAccountCount());
    }

    @Test
    @DisplayName("Get Group Name")
    void getGroupName_ShouldReturnName() {
        assertEquals("مجموعة الأعمال", group.getGroupName());
    }

    @Nested
    @DisplayName("Add and Remove Operations")
    class AddRemoveTests {

        @Test
        @DisplayName("Add Single Account")
        void addAccount_ShouldIncreaseCount() {
            group.add(account1);

            assertEquals(1, group.getAccountCount());
            assertEquals(1000.0, group.getBalance(), 0.001);
            assertTrue(group.getChildren().contains(account1));
        }

        @Test
        @DisplayName("Add Multiple Accounts")
        void addMultipleAccounts_ShouldWork() {
            group.add(account1);
            group.add(account2);
            group.add(account3);

            assertEquals(3, group.getAccountCount());
            assertEquals(6000.0, group.getBalance(), 0.001);
        }

        @Test
        @DisplayName("Add Same Account Twice")
        void addSameAccountTwice_ShouldNotDuplicate() {
            group.add(account1);
            group.add(account1);

            assertEquals(1, group.getAccountCount());
            assertEquals(1000.0, group.getBalance(), 0.001);
        }

        @Test
        @DisplayName("Remove Existing Account")
        void removeAccount_ShouldDecreaseCount() {
            group.add(account1);
            group.add(account2);

            group.remove(account1);

            assertEquals(1, group.getAccountCount());
            assertEquals(2000.0, group.getBalance(), 0.001);
            assertFalse(group.getChildren().contains(account1));
            assertTrue(group.getChildren().contains(account2));
        }

        @Test
        @DisplayName("Remove Non-Existent Account")
        void removeNonExistentAccount_ShouldDoNothing() {
            group.add(account1);

            assertDoesNotThrow(() -> group.remove(account2));

            assertEquals(1, group.getAccountCount());
            assertEquals(1000.0, group.getBalance(), 0.001);
        }

        @Test
        @DisplayName("Remove All Accounts")
        void removeAllAccounts_ShouldEmptyGroup() {
            group.add(account1);
            group.add(account2);

            group.remove(account1);
            group.remove(account2);

            assertEquals(0, group.getAccountCount());
            assertEquals(0.0, group.getBalance(), 0.001);
            assertTrue(group.getChildren().isEmpty());
        }
    }

    @Nested
    @DisplayName("Balance Calculations")
    class BalanceTests {

        @Test
        @DisplayName("Empty Group Balance")
        void emptyGroup_ShouldHaveZeroBalance() {
            assertEquals(0.0, group.getBalance(), 0.001);
        }

        @Test
        @DisplayName("Group Balance - Single Account")
        void groupWithSingleAccount_ShouldMatchAccountBalance() {
            group.add(account1);
            assertEquals(account1.getBalance(), group.getBalance(), 0.001);
        }

        @Test
        @DisplayName("Group Balance - Multiple Accounts")
        void groupWithMultipleAccounts_ShouldSumBalances() {
            group.add(account1);
            group.add(account2);
            group.add(account3);

            assertEquals(6000.0, group.getBalance(), 0.001);
        }

        @Test
        @DisplayName("Balance Updates When Account Changes")
        void balance_ShouldUpdateWhenAccountChanges() {
            group.add(account1);
            group.add(account2);

            double initialBalance = group.getBalance();

            account1.deposit(500.0);

            assertEquals(initialBalance + 500.0, group.getBalance(), 0.001);
        }
    }

    @Nested
    @DisplayName("Deposit Operations")
    class DepositTests {

        @Test
        @DisplayName("Deposit to Empty Group")
        void depositToEmptyGroup_ShouldPrintMessage() {
            java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
            System.setOut(new java.io.PrintStream(outContent));

            try {
                group.deposit(1000.0);

                String output = outContent.toString();
                assertTrue(output.contains("you can't do that") ||
                        output.contains("List is free") ||
                        output.contains("There is no account"));
            } finally {
                System.setOut(System.out);
            }
        }

        @Test
        @DisplayName("Deposit to Single Account Group")
        void depositToSingleAccountGroup_ShouldGoToThatAccount() {
            group.add(account1);
            double initialBalance = account1.getBalance();

            group.deposit(500.0);

            assertEquals(initialBalance + 500.0, account1.getBalance(), 0.001);
            assertEquals(1500.0, group.getBalance(), 0.001);
        }

        @ParameterizedTest
        @CsvSource({
                "1000.0, 2, 500.0",
                "600.0, 3, 200.0",
                "0.0, 2, 0.0"
        })
        @DisplayName("Deposit Distribution to Multiple Accounts")
        void depositToMultipleAccounts_ShouldDistributeEvenly(
                double depositAmount, int accountCount, double expectedShare) {

            group.add(account1);
            group.add(account2);
            if (accountCount > 2) {
                group.add(account3);
            }

            double[] initialBalances = group.getChildren().stream()
                    .mapToDouble(AccountComponent::getBalance)
                    .toArray();

            group.deposit(depositAmount);


            List<AccountComponent> children = group.getChildren();
            for (int i = 0; i < children.size(); i++) {
                double expectedBalance = initialBalances[i] + expectedShare;
                assertEquals(expectedBalance, children.get(i).getBalance(), 0.001);
            }


            double expectedTotal = initialBalances[0] + initialBalances[1];
            if (accountCount > 2) expectedTotal += initialBalances[2];
            expectedTotal += depositAmount;

            assertEquals(expectedTotal, group.getBalance(), 0.001);
        }

        @Test
        @DisplayName("Multiple Deposits")
        void multipleDeposits_ShouldAccumulate() {
            group.add(account1);
            group.add(account2);

            group.deposit(200.0);
            assertEquals(1100.0, account1.getBalance(), 0.001);
            assertEquals(2100.0, account2.getBalance(), 0.001);


            group.deposit(400.0);
            assertEquals(1300.0, account1.getBalance(), 0.001);
            assertEquals(2300.0, account2.getBalance(), 0.001);
        }
    }

    @Nested
    @DisplayName("Withdraw Operations")
    class WithdrawTests {

        @Test
        @DisplayName("Withdraw from Empty Group")
        void withdrawFromEmptyGroup_ShouldReturnFalse() {
            assertFalse(group.withdraw(100.0));
            assertEquals(0.0, group.getBalance(), 0.001);
        }

        @Test
        @DisplayName("Withdraw More Than Total Balance")
        void withdrawMoreThanBalance_ShouldReturnFalse() {
            group.add(account1);
            group.add(account2);


            assertFalse(group.withdraw(4000.0));
            assertEquals(3000.0, group.getBalance(), 0.001);
        }

        @Test
        @DisplayName("Withdraw Exact Balance")
        void withdrawExactBalance_ShouldReturnTrue() {
            group.add(account1); // 1000
            group.add(account2); // 2000
            // Total: 3000

            assertTrue(group.withdraw(3000.0));
            assertEquals(0.0, group.getBalance(), 0.001);
        }

        @Test
        @DisplayName("Withdraw Less Than Balance")
        void withdrawLessThanBalance_ShouldReturnTrue() {
            group.add(account1);
            group.add(account2);


            assertTrue(group.withdraw(1500.0));
            assertTrue(group.getBalance() > 0);
        }

        @Test
        @DisplayName("Withdraw from Single Account")
        void withdrawFromSingleAccountGroup_ShouldUseThatAccount() {
            group.add(account1);

            assertTrue(group.withdraw(500.0));
            assertEquals(500.0, account1.getBalance(), 0.001);
            assertEquals(500.0, group.getBalance(), 0.001);
        }

        @Test
        @DisplayName("Withdraw Using Multiple Accounts")
        void withdrawUsingMultipleAccounts_ShouldTakeFromAll() {
            group.add(new IndividualAccount("حساب أ", "A-001", 500.0));
            group.add(new IndividualAccount("حساب ب", "B-001", 800.0));


            assertTrue(group.withdraw(1000.0));


            List<AccountComponent> children = group.getChildren();
            assertEquals(0.0, children.get(0).getBalance(), 0.001);
            assertEquals(300.0, children.get(1).getBalance(), 0.001);
        }

        @Test
        @DisplayName("Withdraw Zero Amount")
        void withdrawZero_ShouldReturnTrue() {
            group.add(account1);

            assertTrue(group.withdraw(0.0));
            assertEquals(1000.0, group.getBalance(), 0.001); // Balance unchanged
        }

        @Test
        @DisplayName("Withdraw Negative Amount")
        void withdrawNegative_ShouldReturnFalse() {
            group.add(account1);

            assertFalse(group.withdraw(-100.0));
            assertEquals(1000.0, group.getBalance(), 0.001); // Balance unchanged
        }

        @Test
        @DisplayName("Complex Withdraw Scenario")
        void complexWithdrawScenario() {

            IndividualAccount acc1 = new IndividualAccount("Acc1", "A1", 300.0);
            IndividualAccount acc2 = new IndividualAccount("Acc2", "A2", 700.0);
            IndividualAccount acc3 = new IndividualAccount("Acc3", "A3", 1000.0);

            group.add(acc1);
            group.add(acc2);
            group.add(acc3);



            assertTrue(group.withdraw(1500.0));


            assertEquals(0.0, acc1.getBalance(), 0.001);
            assertEquals(0.0, acc2.getBalance(), 0.001);
            assertEquals(500.0, acc3.getBalance(), 0.001);
            assertEquals(500.0, group.getBalance(), 0.001);
        }
    }

    @Nested
    @DisplayName("Children Operations")
    class ChildrenTests {

        @Test
        @DisplayName("Get Children - Empty Group")
        void getChildren_EmptyGroup_ShouldReturnEmptyList() {
            List<AccountComponent> children = group.getChildren();

            assertNotNull(children);
            assertTrue(children.isEmpty());
            assertEquals(0, children.size());
        }

        @Test
        @DisplayName("Get Children - Non-Empty Group")
        void getChildren_ShouldReturnAllAccounts() {
            group.add(account1);
            group.add(account2);

            List<AccountComponent> children = group.getChildren();

            assertEquals(2, children.size());
            assertTrue(children.contains(account1));
            assertTrue(children.contains(account2));
        }

        @Test
        @DisplayName("Get Children - Defensive Copy")
        void getChildren_ShouldReturnDefensiveCopy() {
            group.add(account1);

            List<AccountComponent> children = group.getChildren();
            assertEquals(1, children.size());

            // Modify the returned list should not affect the group
            children.clear();

            // Group should still have the account
            assertEquals(1, group.getAccountCount());
            assertEquals(1, group.getChildren().size());
        }

        @Test
        @DisplayName("Add Nested Group")
        void addNestedGroup_ShouldWork() {
            AccountGroup subGroup = new AccountGroup("مجموعة فرعية", "SUB");
            subGroup.add(account1);

            group.add(subGroup);

            assertEquals(1, group.getAccountCount());
            assertEquals(1000.0, group.getBalance(), 0.001);
        }
    }

    @Nested
    @DisplayName("Display Method")
    class DisplayTests {

        @Test
        @DisplayName("Display Empty Group")
        void displayEmptyGroup_ShouldNotThrow() {
            assertDoesNotThrow(() -> group.display(0));
        }

        @Test
        @DisplayName("Display Single Account Group")
        void displaySingleAccountGroup() {
            group.add(account1);


            java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
            System.setOut(new java.io.PrintStream(outContent));

            try {
                group.display(0);

                String output = outContent.toString();
                assertTrue(output.contains("مجموعة الأعمال"));
                assertTrue(output.contains("BUS-001"));
                assertTrue(output.contains("1000.0"));
                assertTrue(output.contains("عدد الحسابات: 1"));
            } finally {
                System.setOut(System.out);
            }
        }

        @Test
        @DisplayName("Display Multiple Accounts Group")
        void displayMultipleAccountsGroup() {
            group.add(account1);
            group.add(account2);
            group.add(account3);

            assertDoesNotThrow(() -> {
                group.display(0);
                group.display(2);  // With indent
                group.display(4);  // With more indent
            });
        }

        @Test
        @DisplayName("Display with Different Indent Levels")
        void displayWithIndent_ShouldIndentProperly() {
            group.add(account1);
            group.add(account2);

            // Test different indent levels
            for (int indent = 0; indent <= 8; indent += 2) {
                int finalIndent = indent;
                assertDoesNotThrow(() -> group.display(finalIndent));
            }
        }

        @Test
        @DisplayName("Display Nested Groups")
        void displayNestedGroups_ShouldShowHierarchy() {
            AccountGroup subGroup = new AccountGroup("فرعية", "SUB");
            subGroup.add(account1);
            subGroup.add(account2);

            group.add(subGroup);
            group.add(account3);

            assertDoesNotThrow(() -> group.display(0));
        }
    }

    @Test
    @DisplayName("Get Account Count")
    void getAccountCount_ShouldReturnCorrectNumber() {
        assertEquals(0, group.getAccountCount());

        group.add(account1);
        assertEquals(1, group.getAccountCount());

        group.add(account2);
        assertEquals(2, group.getAccountCount());

        group.remove(account1);
        assertEquals(1, group.getAccountCount());
    }
}
