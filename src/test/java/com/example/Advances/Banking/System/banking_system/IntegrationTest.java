//package com.example.Advances.Banking.System.banking_system;
//
//import com.example.Advances.Banking.System.core.enums.AccountType;
//import com.example.Advances.Banking.System.core.model.Account;
//import com.example.Advances.Banking.System.core.model.Customer;
//import com.example.Advances.Banking.System.core.model.Transaction;
//import com.example.Advances.Banking.System.subsystem.account.repository.AccountRepository;
//import com.example.Advances.Banking.System.subsystem.customer.repository.CustomerRepository;
//import com.example.Advances.Banking.System.subsystem.transaction.TransferRequest;
//import com.example.Advances.Banking.System.subsystem.transaction.repository.TransactionRepository;
//import com.example.Advances.Banking.System.subsystem.transaction.service.TransactionService;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@EntityScan(basePackages = {
//        "com.example.Advances.Banking.System.core.model",
//        "com.example.Advances.Banking.System.banking_system"
//})
//@TestPropertySource(locations = "classpath:application-test.properties")
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@Transactional
//class IntegrationTest {
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Autowired
//    private AccountRepository accountRepository;
//
//    @Autowired
//    private TransactionRepository transactionRepository;
//
//    @Autowired
//    private TransactionService transactionService;
//
//    private static Customer savedCustomer1;
//    private static Customer savedCustomer2;
//    private static Account savedAccount1;
//    private static Account savedAccount2;
//
//    @BeforeAll
//    static void beforeAll() {
//        System.out.println("🧪 =======================================");
//        System.out.println("🧪 بدء اختبار التكامل للنظام المصرفي");
//        System.out.println("🧪 =======================================");
//    }
//
//    @Test
//    @Order(1)
//    @DisplayName("1. اختبار إنشاء العملاء")
//    void testCreateCustomers() {
//        System.out.println("\n🧪 اختبار إنشاء العملاء...");
//
//        // إنشاء عملاء
//        Customer customer1 = new Customer("أحمد", "محمد", "ahmed@bank.com");
//        customer1.setPhone("0599123456");
//        customer1.setAddress("غزة - فلسطين");
//
//        Customer customer2 = new Customer("سارة", "خالد", "sara@bank.com");
//        customer2.setPhone("0599876543");
//        customer2.setAddress("رام الله - فلسطين");
//
//        // حفظ في قاعدة البيانات
//        savedCustomer1 = customerRepository.save(customer1);
//        savedCustomer2 = customerRepository.save(customer2);
//
//        System.out.println("✅ العملاء أنشئوا:");
//        System.out.println("   - " + savedCustomer1.getFullName() + " (ID: " + savedCustomer1.getId() + ")");
//        System.out.println("   - " + savedCustomer2.getFullName() + " (ID: " + savedCustomer2.getId() + ")");
//
//        assertNotNull(savedCustomer1.getId());
//        assertNotNull(savedCustomer2.getId());
//    }
//
//    @Test
//    @Order(2)
//    @DisplayName("2. اختبار إنشاء الحسابات")
//    void testCreateAccounts() {
//        System.out.println("\n🧪 اختبار إنشاء الحسابات...");
//
//        // تأكد من وجود العملاء
//        if (savedCustomer1 == null || savedCustomer2 == null) {
//            savedCustomer1 = customerRepository.findAll().stream()
//                    .filter(c -> c.getEmail().equals("ahmed@bank.com"))
//                    .findFirst()
//                    .orElseThrow();
//
//            savedCustomer2 = customerRepository.findAll().stream()
//                    .filter(c -> c.getEmail().equals("sara@bank.com"))
//                    .findFirst()
//                    .orElseThrow();
//        }
//
//        // إنشاء حسابات
//        Account account1 = new Account();
//        account1.setAccountType(AccountType.SAVINGS);
//        account1.setCustomer(savedCustomer1);
//        account1.setBalance(5000.0);
//        account1.setAccountNumber("SAV-001");
//
//        Account account2 = new Account();
//        account2.setAccountType(AccountType.CHECKING);
//        account2.setCustomer(savedCustomer2);
//        account2.setBalance(3000.0);
//        account2.setAccountNumber("CUR-001");
//
//        // حفظ في قاعدة البيانات
//        savedAccount1 = accountRepository.save(account1);
//        savedAccount2 = accountRepository.save(account2);
//
//        System.out.println("✅ الحسابات أنشئت:");
//        System.out.println("   - حساب " + savedAccount1.getAccountNumber() + " - الرصيد: $" + savedAccount1.getBalance());
//        System.out.println("   - حساب " + savedAccount2.getAccountNumber() + " - الرصيد: $" + savedAccount2.getBalance());
//
//        assertNotNull(savedAccount1.getId());
//        assertNotNull(savedAccount2.getId());
//    }
//
//    @Test
//    @Order(3)
//    @DisplayName("3. اختبار عمليات الإيداع")
//    void testDepositOperations() {
//        System.out.println("\n🧪 اختبار عمليات الإيداع...");
//
//        // البحث عن الحساب
//        Optional<Account> accountOpt = accountRepository.findByAccountNumber("SAV-001");
//        assertTrue(accountOpt.isPresent());
//        Account account = accountOpt.get();
//
//        double initialBalance = account.getBalance();
//        System.out.println("💰 الرصيد الأولي: $" + initialBalance);
//
//        // إيداع مبلغ
//        account.deposit(1500.0);
//        accountRepository.save(account);
//
//        // التحقق
//        Account updatedAccount = accountRepository.findByAccountNumber("SAV-001").get();
//        double newBalance = updatedAccount.getBalance();
//
//        System.out.println("✅ بعد إيداع $1500:");
//        System.out.println("   الرصيد الجديد: $" + newBalance);
//
//        assertEquals(initialBalance + 1500.0, newBalance, 0.001);
//    }
//
//    @Test
//    @Order(4)
//    @DisplayName("4. اختبار عمليات السحب")
//    void testWithdrawalOperations() {
//        System.out.println("\n🧪 اختبار عمليات السحب...");
//
//        Optional<Account> accountOpt = accountRepository.findByAccountNumber("CUR-001");
//        assertTrue(accountOpt.isPresent());
//        Account account = accountOpt.get();
//
//        double initialBalance = account.getBalance();
//        System.out.println("💰 الرصيد الأولي: $" + initialBalance);
//
//        // سحب مبلغ
//        account.withdraw(800.0);
//        accountRepository.save(account);
//
//        // التحقق
//        Account updatedAccount = accountRepository.findByAccountNumber("CUR-001").get();
//        double newBalance = updatedAccount.getBalance();
//
//        System.out.println("✅ بعد سحب $800:");
//        System.out.println("   الرصيد الجديد: $" + newBalance);
//
//        assertEquals(initialBalance - 800.0, newBalance, 0.001);
//    }
//
//    @Test
//    @Order(5)
//    @DisplayName("5. اختبار سحب برصيد غير كافي")
//    void testInsufficientFunds() {
//        System.out.println("\n🧪 اختبار سحب برصيد غير كافي...");
//
//        Optional<Account> accountOpt = accountRepository.findByAccountNumber("SAV-001");
//        assertTrue(accountOpt.isPresent());
//        Account account = accountOpt.get();
//
//        double currentBalance = account.getBalance();
//        System.out.println("💰 الرصيد الحالي: $" + currentBalance);
//        System.out.println("🔄 محاولة سحب $" + (currentBalance + 5000));
//
//        // محاولة سحب مبلغ أكبر من الرصيد
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            account.withdraw(currentBalance + 5000);
//        });
//
//        System.out.println("✅ تم منع السحب بنجاح:");
//        System.out.println("   الخطأ: " + exception.getMessage());
//
//        // التأكد من أن الرصيد لم يتغير
//        Account unchangedAccount = accountRepository.findByAccountNumber("SAV-001").get();
//        assertEquals(currentBalance, unchangedAccount.getBalance(), 0.001);
//    }
//
//    @Test
//    @Order(6)
//    @DisplayName("6. اختبار استعلامات قاعدة البيانات")
//    void testDatabaseQueries() {
//        System.out.println("\n🧪 اختبار استعلامات قاعدة البيانات...");
//
//        // 1. عد جميع العملاء
//        long customerCount = customerRepository.count();
//        System.out.println("👥 عدد العملاء: " + customerCount);
//        assertTrue(customerCount >= 2);
//
//        // 2. عد جميع الحسابات
//        long accountCount = accountRepository.count();
//        System.out.println("🏦 عدد الحسابات: " + accountCount);
//        assertTrue(accountCount >= 2);
//
//        // 3. إجمالي الأموال في البنك
//        List<Account> allAccounts = accountRepository.findAll();
//        double totalBankMoney = allAccounts.stream()
//                .mapToDouble(Account::getBalance)
//                .sum();
//
//        System.out.println("💰 إجمالي أموال البنك: $" + String.format("%.2f", totalBankMoney));
//        assertTrue(totalBankMoney > 0);
//    }
//
//    @Test
//    @Order(7)
//    @DisplayName("7. اختبار نهائي وطباعة التقرير")
//    void testFinalReport() {
//        System.out.println("\n📊 ===============================");
//        System.out.println("📊 تقرير اختبار التكامل النهائي");
//        System.out.println("📊 ===============================");
//
//        // جمع الإحصائيات
//        long totalCustomers = customerRepository.count();
//        long totalAccounts = accountRepository.count();
//        double totalBankMoney = accountRepository.findAll().stream()
//                .mapToDouble(Account::getBalance)
//                .sum();
//
//        System.out.println("👥 العملاء: " + totalCustomers);
//        System.out.println("🏦 الحسابات: " + totalAccounts);
//        System.out.println("💰 إجمالي أموال البنك: $" + String.format("%.2f", totalBankMoney));
//
//        // طباعة تفاصيل العملاء
//        System.out.println("\n📋 تفاصيل العملاء:");
//        customerRepository.findAll().forEach(customer -> {
//            System.out.println("   👤 " + customer.getFullName());
//            System.out.println("      📧 " + customer.getEmail());
//            System.out.println("      📞 " + customer.getPhone());
//            System.out.println("      🏦 عدد الحسابات: " + customer.getAccounts().size());
//        });
//
//        // التحقق النهائي
//        assertTrue(totalCustomers > 0, "يجب أن يكون هناك عملاء");
//        assertTrue(totalAccounts > 0, "يجب أن يكون هناك حسابات");
//        assertTrue(totalBankMoney > 0, "يجب أن يكون هناك أموال في البنك");
//
//        System.out.println("\n✅ جميع اختبارات التكامل نجحت!");
//        System.out.println("🎉 النظام البنكي جاهز للتشغيل!");
//    }
//
//    @AfterAll
//    static void afterAll() {
//        System.out.println("\n🧹 =======================================");
//        System.out.println("🧹 انتهى اختبار التكامل");
//        System.out.println("🧹 =======================================");
//        System.out.println("✅ ملاحظة: جميع البيانات نظفت تلقائياً بفضل @Transactional");
//    }
//}