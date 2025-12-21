package com.example.Advances.Banking.System.banking_system;

import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;
import com.example.Advances.Banking.System.subsystem.transaction.TransferRequest;
import com.example.Advances.Banking.System.subsystem.transaction.service.TransactionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AdvancesBankingSystemApplication  {

    public static void main(String[] args) {
        SpringApplication.run(AdvancesBankingSystemApplication.class, args);
        System.out.println("🧪 بدء اختبار التحويل اليدوي...");

        Customer customer1 = new Customer();
        customer1.setFirstName("John");
        customer1.setLastName("Doe");
        customer1.setEmail("john@example.com");
        customer1.setPhone("1234567890");

        Customer customer2 = new Customer();
        customer2.setFirstName("Jane");
        customer2.setLastName("Smith");
        customer2.setEmail("jane@example.com");
        customer2.setPhone("0987654321");


        Account account1 = new Account(AccountType.SAVINGS, customer1, 1000.0);
        account1.setAccountNumber("ACC123456");

        Account account2 = new Account(AccountType.CHECKING, customer2, 500.0);
        account2.setAccountNumber("ACC789012");


        customer1.addAccount(account1);
        customer2.addAccount(account2);

        System.out.println("📊 قبل التحويل:");
        System.out.println("   الحساب 1 (" + account1.getAccountNumber() +
                ") للموكل: " + customer1.getFullName() +
                " - الرصيد: $" + account1.getBalance());
        System.out.println("   الحساب 2 (" + account2.getAccountNumber() +
                ") للموكل: " + customer2.getFullName() +
                " - الرصيد: $" + account2.getBalance());


        account1.deposit(500.0);
        System.out.println("\n✅ بعد إيداع $500 في الحساب 1:");
        System.out.println("   رصيد الحساب 1: $" + account1.getBalance());


        account2.withdraw(200.0);
        System.out.println("✅ بعد سحب $200 من الحساب 2:");
        System.out.println("   رصيد الحساب 2: $" + account2.getBalance());


        System.out.println("\n📊 الرصيد النهائي:");
        System.out.println("   الحساب 1: $" + account1.getBalance() + " (متوقع: 1500.0)");
        System.out.println("   الحساب 2: $" + account2.getBalance() + " (متوقع: 300.0)");


        System.out.println("\n💰 الرصيد الإجمالي للموكلين:");
        System.out.println("   " + customer1.getFullName() +
                " الرصيد الإجمالي: $" + customer1.getTotalBalance());
        System.out.println("   " + customer2.getFullName() +
                " الرصيد الإجمالي: $" + customer2.getTotalBalance());


        if (Math.abs(account1.getBalance() - 1500.0) < 0.01 &&
                Math.abs(account2.getBalance() - 300.0) < 0.01) {
            System.out.println("\n🎉 جميع الاختبارات نجحت!");
        } else {
            System.out.println("\n❌ هناك خطأ في الحسابات!");
        }
    }
}