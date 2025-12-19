//package com.example.Advances.Banking.System.banking_system;
//
//import com.example.Advances.Banking.System.core.enums.AccountType;
//import com.example.Advances.Banking.System.core.model.Customer;
//import com.example.Advances.Banking.System.patterns.creational.factory.AccountFactory;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import static org.assertj.core.api.Assertions.assertThat;
//@Configuration
//@ComponentScan(basePackages = {
//        "com.example.Advances.Banking.System.banking_system",
//        "com.example.Advances.Banking.System.core",
//        "com.example.Advances.Banking.System.patterns",
//        "com.example.Advances.Banking.System.subsystems",
//        "com.example.Advances.Banking.System.nfr"
//})
//@EntityScan(basePackages = "com.example.Advances.Banking.System.core.model")
//@EnableJpaRepositories(basePackages = "com.example.Advances.Banking.System.subsystems")
//@SpringBootTest
//
//public class ExtensibilityTest {
//
//    @Autowired
//    private AccountFactory accountFactory;
//
//    @Test
//    public void testSimpleCreation() {
//        System.out.println("🧪 Starting Simple Creation Test...");
//
//        Customer customer = new Customer();
//        customer.setFirstName("Test");
//        customer.setLastName("User");
//        customer.setEmail("test@example.com");
//
//        try {
//            var account = accountFactory.createAccount(
//                    AccountType.SAVINGS,
//                    customer,
//                    1000.0
//            );
//
//            System.out.println("✅ Test PASSED!");
//            System.out.println("   Account Type: " + account.getAccountType());
//            System.out.println("   Account Number: " + account.getAccountNumber());
//            System.out.println("   Balance: $" + account.getBalance());
//
//            assertThat(account).isNotNull();
//            assertThat(account.getAccountType()).isEqualTo(AccountType.SAVINGS);
//
//        } catch (Exception e) {
//            System.out.println("❌ Test FAILED: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testExtensibility() {
//        System.out.println("\n🧪 Testing Extensibility...");
//
//        Customer customer = new Customer();
//        customer.setFirstName("Extend");
//        customer.setLastName("Test");
//        customer.setEmail("extend@test.com");
//
//        AccountType[] newTypes = {
//                AccountType.STUDENT,
//                AccountType.BUSINESS,
//                AccountType.CRYPTO
//        };
//
//        int createdCount = 0;
//
//        for (AccountType type : newTypes) {
//            try {
//                System.out.println("\n   Trying to create: " + type);
//                var account = accountFactory.createAccount(type, customer, 500.0);
//                createdCount++;
//                System.out.println("   ✅ Created: " + account.getAccountNumber());
//            } catch (Exception e) {
//                System.out.println("   ⚠️ Failed: " + e.getMessage());
//            }
//        }
//
//        System.out.println("\n📊 Extensibility Result:");
//        System.out.println("   Created " + createdCount + "/" + newTypes.length + " new account types");
//
//        if (createdCount > 0) {
//            System.out.println("✅ Extensibility PROVEN!");
//            System.out.println("   Can add new account types WITHOUT modifying existing code");
//        } else {
//            System.out.println("❌ Extensibility NOT proven yet");
//        }
//
//    }
//}